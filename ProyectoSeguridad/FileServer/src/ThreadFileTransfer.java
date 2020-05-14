import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.interfaces.*;
import javax.crypto.spec.*;

public class ThreadFileTransfer extends Thread {

	private ObjectInputStream in;
	private ObjectOutputStream out;

	Socket socket;

	private DiffieHellmanKex dh = new DiffieHellmanKex();

	public ThreadFileTransfer(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("resource")
	public void run() {
		try {

			//Negociacion de la clave simetrica
			DHPublicKey publicKey;
			KeyPair keyPair;
			byte[] secret;

			InputStream is = socket.getInputStream();
			in = new ObjectInputStream(is);
			out = new ObjectOutputStream(socket.getOutputStream());

			//Recepcion de los valores del cliente
			publicKey = dh.getServerPublic(in);

			keyPair = dh.generateKeyPair();

			//Envio de la clave publica al cliente
			dh.passPublicToServer((DHPublicKey) keyPair.getPublic(), out);

			//Se genera la clave privada
			secret = dh.computeDHSecretKey((DHPrivateKey) keyPair.getPrivate(), publicKey);

			secret = MessageDigest.getInstance("MD5").digest(secret);
			byte[] symmetricKey = Arrays.copyOf(secret, 16);


			//Se inicializan los parametros para hacer la desencripcion
			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec ky = new SecretKeySpec(symmetricKey, "AES");

			ci.init(Cipher.DECRYPT_MODE, ky, new IvParameterSpec(new byte[16]));


			//Recepcion del archivo encriptado
			byte[] mybytearray = new byte[100000000];
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			int bytesRead;
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			mybytearray = Arrays.copyOf(mybytearray, bytesRead);
			
			// Se imprime el archivo encriptado recibido 
			fos = new FileOutputStream("./docs/receivedFileEncrypted.txt");
			bos = new BufferedOutputStream(fos);
			bos.write(mybytearray, 0, mybytearray.length);
			bos.flush();

			//Desencripcion del archivo
			mybytearray = ci.doFinal(mybytearray);


			//Calculo del hash SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(mybytearray);
			byte[] shaHash = md.digest();
			System.out.println("El SHA-1 es "+new String(shaHash));
			System.out.println("Archivo transferido correctamente");

			// Se imprime el archivo desencriptado recibido 
			fos = new FileOutputStream("./docs/receivedFilePlain.txt");
			bos = new BufferedOutputStream(fos);
			bos.write(mybytearray, 0, mybytearray.length);
			bos.flush();


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainClass {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	private int puerto = 30000;
	private String host = "127.0.0.1";
	private String pathname = "";

	private DiffieHellmanKex dh = new DiffieHellmanKex();

	public MainClass() {
		try {
			socket = new Socket(host, puerto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public void transferFile() {
		try {
			OutputStream os = socket.getOutputStream();
			out = new ObjectOutputStream(os);
			DHPublicKey publicKey;
			KeyPair keyPair;
			byte[] secret;

			// Generacion de la llave publica
			keyPair = dh.generateKeyPair();

			// Se envia los valores al servidor
			dh.passPublicToServer((DHPublicKey) keyPair.getPublic(), out);

			in = new ObjectInputStream(socket.getInputStream());

			// Se recive la clave publica del servidor
			publicKey = dh.getServerPublic(in);

			// Se calcula la clave privada
			secret = dh.computeDHSecretKey((DHPrivateKey) keyPair.getPrivate(), publicKey);
			secret = MessageDigest.getInstance("MD5").digest(secret);
			byte[] symmetricKey = Arrays.copyOf(secret, 16);

			// Se inicializan los parametros para hacer la encriptacion
			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec ky = new SecretKeySpec(symmetricKey, "AES");
			ci.init(Cipher.ENCRYPT_MODE, ky, new IvParameterSpec(new byte[16]));

			FileInputStream fis = null;
			BufferedInputStream bis = null;
			File myFile = new File(pathname);
			byte[] mybytearray = new byte[(int) myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);

			FileOutputStream fos = null;
			BufferedOutputStream bos = null;

			// Se imprime el archivo en texto plano
			fos = new FileOutputStream("./docs/plainText.txt");
			bos = new BufferedOutputStream(fos);
			bos.write(mybytearray, 0, mybytearray.length);
			bos.flush();

			// Se calcula el SHA-1 para el archivo antes de ser encriptado
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(mybytearray);
			byte[] shaHash = md.digest();

			// Se encripta el archivo
			mybytearray = ci.doFinal(mybytearray);
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();

			// Se imprime el archivo encriptado
			fos = new FileOutputStream("./docs/fileEncrypted.txt");
			bos = new BufferedOutputStream(fos);
			bos.write(mybytearray, 0, mybytearray.length);
			bos.flush();

			System.out.println("El SHA-1 es " + new String(shaHash));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public DiffieHellmanKex getDh() {
		return dh;
	}

	public void setDh(DiffieHellmanKex dh) {
		this.dh = dh;
	}
}
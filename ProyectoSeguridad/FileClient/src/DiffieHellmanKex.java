import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHPublicKeySpec;

public class DiffieHellmanKex {
  public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
    final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
    keyPairGenerator.initialize(1024);
    return keyPairGenerator.generateKeyPair();
  }
  
  public void passPublicToServer(DHPublicKey publicKey, ObjectOutputStream out) throws Exception {
    BigInteger p, g, y;
    p = publicKey.getParams().getP();
    g = publicKey.getParams().getG();
    y = publicKey.getY();
    out.writeObject(p);
    out.writeObject(g);
    out.writeObject(y);
    out.flush();
  }
  public DHPublicKey getServerPublic(ObjectInputStream in) throws Exception {
    KeyFactory factory = KeyFactory.getInstance("DH");
    BigInteger p, g, y;
    p = (BigInteger) in.readObject();
    g = (BigInteger) in.readObject();
    y = (BigInteger) in.readObject();
    KeySpec spec = new DHPublicKeySpec(y, p, g);
    DHPublicKey publicKey = (DHPublicKey) factory.generatePublic(spec);
    return publicKey;
  }
  public byte[] computeDHSecretKey(DHPrivateKey privateKey, DHPublicKey publicKey) throws Exception {
    final KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
    keyAgreement.init(privateKey);
    keyAgreement.doPhase(publicKey, true);
    byte[] commonSecret = keyAgreement.generateSecret();
    return commonSecret;
  }
}
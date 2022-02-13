package r3mote.Backend;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Security {
    
    private SecretKey key;
    private int KEY_SIZE = 128;
    private byte[] IV;
    private int T_LEN = 128;            //tag length


    public void init() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
        System.out.println(key);

    }
    
    public String encrypt(String rawPassword) throws Exception {

        byte[] passwordInBytes = rawPassword.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        IV = encryptionCipher.getIV();
        byte[] encryptedBytes = encryptionCipher.doFinal(passwordInBytes);
        return encode(encryptedBytes);

    }

    public String decrypt(String encryptedPassword) throws Exception{

        byte[] passwordInBytes = decode(encryptedPassword);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN,IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(passwordInBytes);

        return new String(decryptedBytes);
    }
    private byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }

    private String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    //dbg
    public void printTest() throws Exception{

        String pass = "hi";
        String encrypted = encrypt(pass);
        String decryption = decrypt(encrypted);
        System.out.println("Original: " + pass);
        System.out.println("Encrypted Password : " + encrypted);
        System.out.println("Decrypted Password : " + decryption);
    }
}

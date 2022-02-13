package r3mote.Backend;


import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;


public class Security {
    
    private SecretKey key;
    private int KEY_SIZE = 128;
    private Cipher encryptionCipher;
    private int T_LEN = 128;


    public void init() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();

    }
    
    public String encryptPassword(String rawPassword) throws Exception{

        byte[] passwordInBytes = rawPassword.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(passwordInBytes);
        return encode(encryptedBytes);

    }

    public String decryptPassword(String encryptedPassword) throws Exception {

        byte[] passwordInBytes = decode(encryptedPassword);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
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
}

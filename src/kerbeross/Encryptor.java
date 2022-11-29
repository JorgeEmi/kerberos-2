/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kerbeross;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author jorge
 */
public class Encryptor {
    public Encryptor() {
    }

    public byte[] AESEncryption(SecretKey key, String toEncrypt) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

        Cipher cipher = Cipher.getInstance("AES");
        byte[] bytes = toEncrypt.getBytes("UTF8");

        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherBytes = cipher.doFinal(bytes);

        return cipherBytes;
    }
    
    
    public byte[] AESDecryption(SecretKey key, byte[] toDescrypt) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(toDescrypt);

        return bytes;
    }
    public static String encrypt(Cipher encryptCipher, String toEncrypt) throws Exception {
        byte[] bytesToEncrypt = toEncrypt.getBytes(StandardCharsets.UTF_8);
        byte[] bytesEncrypted = encryptCipher.doFinal(bytesToEncrypt);
        bytesEncrypted = Base64.getEncoder().encode(bytesEncrypted);
        return new String(bytesEncrypted);
    }

    public static String decrypt(Cipher decryptCypher, String toDecrypt) throws Exception {
        byte[] bytesToDecrypt = Base64.getDecoder().decode(toDecrypt.getBytes());
//        byte[] bytesToDecrypt = toDecrypt.getBytes();
        byte[] bytesDecrypted = decryptCypher.doFinal(bytesToDecrypt);
        return new String(bytesDecrypted);
    }
    
    public static String symmetricEncrypt(SecretKey secretKey, String toEncrypt) throws Exception {
        Cipher encryptCypher = Cipher.getInstance("DES");
        encryptCypher.init(Cipher.ENCRYPT_MODE, secretKey);
        return encrypt(encryptCypher, toEncrypt);
    }

    public static String symmetricDecrypt(SecretKey secretKey, String toDecrypt) throws Exception {
        Cipher decryptCypher = Cipher.getInstance("DES");
        decryptCypher.init(Cipher.DECRYPT_MODE, secretKey);
        return decrypt(decryptCypher, toDecrypt);
    }
}

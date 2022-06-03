package woowacourse.shoppingcart.support;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256Encryptor implements Encryptor{

    @Override
    public String encrypt(String plainText){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(plainText.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch(NoSuchAlgorithmException e){
            throw new RuntimeException();
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}

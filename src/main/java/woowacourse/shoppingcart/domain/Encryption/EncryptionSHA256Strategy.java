package woowacourse.shoppingcart.domain.Encryption;

import woowacourse.shoppingcart.domain.customer.Customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionSHA256Strategy implements EncryptionStrategy {

    public Customer encrypt(final Customer customer) {
        return Customer.getEncrypted(
                customer.getId(), customer.getUserId(), customer.getNickname(), encrypt(customer.getPassword())
        );
    }

    private static String encrypt(final String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    private static String bytesToHex(final byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}

package woowacourse.shoppingcart.domain.customer.values.password;

public class EncryptedPassword {

    private static final int ENCRYPTED_LENGTH = 64;

    private final String password;

    public EncryptedPassword(final String password) {
        validateLength(password);
        this.password = password;
    }

    private void validateLength(final String password) {
        if (password.length() != ENCRYPTED_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("암호화 된 비밀번호는 %d자여야 합니다.", ENCRYPTED_LENGTH));
        }
    }

    public String getPassword() {
        return password;
    }
}

package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class EncodePassword {
    private static final int ENCODE_PASSWORD_LENGTH = 64;

    private final String password;

    public EncodePassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    private void validatePassword(String password) {
        if (password.length() != ENCODE_PASSWORD_LENGTH) {
            throw new IllegalStateException("암호화 비밀번호의 길이가 올바르지 않습니다.");
        }
    }

    public boolean hasSamePassword(EncodePassword encodePassword) {
        return this.password.equals(encodePassword.getPassword());
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncodePassword that = (EncodePassword) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}

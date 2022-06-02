package woowacourse.auth.domain;

import java.util.Objects;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class EncryptedPassword {

    private final String value;

    public EncryptedPassword(String value) {
        this.value = value;
    }

    public boolean hasSamePassword(String password) {
        try {
            return BCrypt.checkpw(password, value);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("비밀번호가 제대로 암호화되지 않았습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EncryptedPassword that = (EncryptedPassword) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "EncryptedPassword{" + value + '}';
    }
}

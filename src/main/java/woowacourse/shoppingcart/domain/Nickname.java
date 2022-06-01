package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Nickname {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[ㄱ-ㅎ가-힣]{1,5}$");

    private final String value;

    public Nickname(String value) {
        validateNicknameFormat(value);
        this.value = value;
    }

    private void validateNicknameFormat(String nickname) {
        if (!NICKNAME_PATTERN.matcher(nickname).matches()) {
            throw new InvalidCustomerException("잘못된 닉네임 형식입니다.");
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
        Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

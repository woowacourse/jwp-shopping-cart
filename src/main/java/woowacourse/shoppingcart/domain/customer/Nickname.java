package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class Nickname {

    private final static Pattern NICKNAME_FORMAT = Pattern.compile("^(?=.*[a-z0-9가-힣ㄱ-ㅎㅏ-ㅣ])[a-z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{2,10}$");

    private final String value;

    public Nickname(final String value) {
        validateNickname(value);
        this.value = value;
    }

    private void validateNickname(final String value) {
        if (isEmpty(value)) {
            throw new IllegalArgumentException("닉네임을 입력해주세요.");
        }
        if (isNotValidFormat(value)) {
            throw new IllegalArgumentException("닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.");
        }
    }

    private boolean isEmpty(final String value) {
        return value == null || value.isBlank();
    }

    private boolean isNotValidFormat(final String value) {
        return !NICKNAME_FORMAT.matcher(value).matches();
    }

    public String getValue() {
        return value;
    }
}

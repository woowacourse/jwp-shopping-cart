package woowacourse.shoppingcart.domain.customer;

public class Nickname {

    private static final int NICKNAME_MIN_LENGTH_LIMIT = 2;
    private static final int NICKNAME_MAX_LENGTH_LIMIT = 10;
    private final String value;

    public Nickname(String nickname) {
        validateNicknameLength(nickname);
        this.value = nickname;
    }

    private void validateNicknameLength(String nickname) {
        if (nickname.length() < NICKNAME_MIN_LENGTH_LIMIT || nickname.length() > NICKNAME_MAX_LENGTH_LIMIT) {
            throw new IllegalArgumentException("닉네임 길이는 2~10자를 만족해야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}

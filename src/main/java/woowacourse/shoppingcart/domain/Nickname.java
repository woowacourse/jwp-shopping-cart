package woowacourse.shoppingcart.domain;

public class Nickname {

    private static final int NICKNAME_MINIMUM_LENGTH = 2;
    private static final int NICKNAME_MAXIMUM_LENGTH = 10;

    private final String value;

    public Nickname(final String value) {
        validateNickname(value);
        this.value = value;
    }

    private void validateNickname(final String nickname) {
        validateBlank(nickname);
        validateLength(nickname);
    }

    private void validateBlank(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 비어있을 수 없습니다.");
        }
    }

    private void validateLength(String nickname) {
        if (nickname.length() < NICKNAME_MINIMUM_LENGTH || nickname.length() > NICKNAME_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("닉네임 길이는 %d~%d자를 만족해야 합니다.", NICKNAME_MINIMUM_LENGTH, NICKNAME_MAXIMUM_LENGTH)
            );
        }
    }

    public String getValue() {
        return value;
    }
}

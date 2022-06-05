package woowacourse.shoppingcart.domain.customer;

public class NickName {
    private static final int MAX_NICKNAME_LENGTH = 10;

    private final String nickName;

    public NickName(String nickName) {
        validateNickName(nickName);
        this.nickName = nickName;
    }

    private void validateNickName(String nickName) {
        if (nickName.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
        if (nickName.length() > MAX_NICKNAME_LENGTH) {
            throw new IllegalArgumentException("이름의 길이는 10자 이하여야 합니다.");
        }
    }

    public String getNickName() {
        return nickName;
    }
}

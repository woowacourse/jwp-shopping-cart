package woowacourse.shoppingcart.domain;

public class Customer {

    private final String userId;
    private final String nickname;
    private final String password;

    public Customer(final String userId, final String nickname, final String password) {
        validateUserId(userId);
        validateNickname(nickname);
        validatePassword(password);
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }

    private void validateUserId(final String userId) {
        if (isEmpty(userId)) {
            throw new IllegalArgumentException("아이디를 입력해주세요.");
        }
    }

    private void validateNickname(final String nickname) {
        if (isEmpty(nickname)) {
            throw new IllegalArgumentException("닉네임을 입력해주세요.");
        }
    }

    private void validatePassword(final String password) {
        if (isEmpty(password)) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }

    private boolean isEmpty(final String userId) {
        return userId == null || userId.isBlank();
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }
}

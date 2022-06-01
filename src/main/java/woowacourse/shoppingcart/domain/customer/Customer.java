package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class Customer {

    private final UserId userId;
    private final String nickname;
    private final String password;

    public Customer(final String userId, final String nickname, final String password) {
        validateCustomer(userId, nickname, password);
        this.userId = new UserId(userId);
        this.nickname = nickname;
        this.password = password;
    }

    private void validateCustomer(final String userId, final String nickname, final String password) {
        validateNickname(nickname);
        validatePassword(password);
    }

//    private void validateUserId(final String userId) {
//        if (isEmpty(userId)) {
//            throw new IllegalArgumentException("아이디를 입력해주세요.");
//        }
//        if (isNotValidFormat(userId)) {
//            throw new IllegalArgumentException("아이디는 이메일 형식으로 입력해주세요.");
//        }
//    }

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

    private boolean isEmpty(final String value) {
        return value == null || value.isBlank();
    }

//    private boolean isNotValidFormat(final String value) {
//        return !EMAIL_FORMAT.matcher(value).matches();
//    }

    public String getUserId() {
        return userId.getValue();
    }

    public String getNickname() {
        return nickname;
    }
}

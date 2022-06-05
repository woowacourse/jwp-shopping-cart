package woowacourse.shoppingcart.domain;

public class Password {
    private static final String KOREAN_REGEX = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
    private static final String BLANK = " ";
    private static final int LOWER_BOUND_LENGTH = 6;
    private final String password;

    public Password(String password) {
        checkValid(password);
        this.password = password;
    }

    private void checkValid(String password) {
        if (password.matches(KOREAN_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 비밀번호에 한글이 포함될수 없습니다.");
        }
        if (password.contains(BLANK)) {
            throw new IllegalArgumentException("[ERROR] 비밀번호에 공백이 포함될수 없습니다.");
        }
        if (password.length() < LOWER_BOUND_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 비밀번호의 길이는 6자 이상이어야 합니다.");
        }
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }
}

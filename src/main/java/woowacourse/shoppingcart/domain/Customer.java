package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;

public class Customer {
    private final Long id;
    private final String loginId;
    private final String userName;
    private final String password;

    public Customer(String loginId, String userName, String password) {
        this(null, loginId, userName, password);
    }

    public Customer(Long id, String loginId, String userName, String password) {
        validateLoginId(loginId);
        validatePassword(password);
        this.id = id;
        this.loginId = loginId;
        this.userName = userName;
        this.password = password;
    }

    private void validateLoginId(String loginId) {
        String emailRegex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        if (!Pattern.matches(emailRegex, loginId)) {
            throw new IllegalArgumentException("아이디 형식이 잘못되었습니다.");
        }
    }

    private void validatePassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$";
        if (!Pattern.matches(passwordRegex, password)) {
            throw new IllegalArgumentException("비밀번호 형식이 잘못되었습니다.");
        }
    }
}

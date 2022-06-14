package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

import java.util.regex.Pattern;

public class Customer {

    private static final Pattern EMAIL = Pattern.compile("^(.+)@(.+)$");
    private static final Pattern NOT_PERMIT = Pattern.compile(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
    private static final int USERNAME_MAX_SIZE = 32;
    private static final int EMAIL_MAX_SIZE = 64;
    private static final int PASSWORD_MIN_SIZE = 6;

    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    public Customer(Long id, String username, String email, String password) {
        validateFormat(username, email, password);
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer(String username, String email, String password) {
        this(null, username, email, password);
    }

    private void validateFormat(String username, String email, String password) {
        if (username == null || email == null || password == null) {
            throw new IllegalArgumentException("유저 네임과 이메일, 비밀번호는 null이 안됩니다.");
        }

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("유저 네임과 이메일, 비밀번호를 모두 입력해주세요.");
        }
        validateSize(username, email, password);
        validatePattern(email, password);
    }

    private void validateSize(String username, String email, String password) {
        if (username.length() > USERNAME_MAX_SIZE) {
            throw new IllegalArgumentException("유저네임은 32자 이하로 작성해주세요.");
        }
        if (email.length() > EMAIL_MAX_SIZE) {
            throw new IllegalArgumentException("이메일은 64자 이하로 작성해주세요.");
        }
        if (password.length() < PASSWORD_MIN_SIZE) {
            throw new IllegalArgumentException("비밀 번호는 6자 이상으로 작성해주세요.");
        }
    }

    private void validatePattern(String email, String password) {
        if (NOT_PERMIT.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일에 한글을 입력할 수 없습니다.");
        }
        if (!EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 형식에 맞춰주세요.");
        }
        if (NOT_PERMIT.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호에 한글을 입력할 수 없습니다.");
        }
    }

    public void isValidPassword(String password) {
        if (!this.password.equals(password)) {
                throw new InvalidPasswordException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

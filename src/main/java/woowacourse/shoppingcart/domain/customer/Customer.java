package woowacourse.shoppingcart.domain.customer;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.regex.Pattern;

/*
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$", message = "잘못된 비밀번호 형식입니다.")
    private String password;

    @Pattern(regexp = "^[가-힣A-Za-z0-9]{2,8}$
 */

public class Customer {

    private final Long id;
    private final String email;
    private final Password password;
    private final String nickname;

    public Customer(String email, String password, String nickname) {
        this(null, email, password, nickname);
    }

    public Customer(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = new Password(password);
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getNickname() {
        return nickname;
    }

    public Long getId() {
        return id;
    }

    public boolean isValidPassword(String password) {
        return this.password.isValidPassword(password);
    }
}

package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.EmailValidationException;
import woowacourse.shoppingcart.exception.UsernameValidationException;
import woowacourse.utils.StringValidator;

public class Customer {

    private static final int EMAIL_MIN_LENGTH = 8;
    private static final int EMAIL_MAX_LENGTH = 50;
    private static final int USERNAME_MIN_LENGTH = 1;
    private static final int USERNAME_MAX_LENGTH = 10;
    private final Long id;
    private final String email;
    private final String password;
    private final String username;

    public Customer(final Long id, final String email, final String password, final String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static Customer createWithoutId(final String email, final String password, final String username) {
        validateEmail(email);
        validateUsername(username);
        return new Customer(null, email, password, username);
    }

    public static Customer createWithoutPassword(final Long id, final String email, final String username) {
        validateEmail(email);
        validateUsername(username);
        return new Customer(id, email, null, username);
    }

    public static Customer createWithoutEmailAndPassword(final Long id, final String username) {
        validateUsername(username);
        return new Customer(id, null, null, username);
    }

    private static void validateEmail(final String email) {
        StringValidator.validateNullOrBlank(email, new EmailValidationException("이메일에는 공백이 들어가면 안됩니다."));
        StringValidator.validateLength(EMAIL_MIN_LENGTH, EMAIL_MAX_LENGTH, email, new EmailValidationException("이메일은 8자 이상 50자 이하여야합니다."));
    }

    private static void validateUsername(final String username) {
        StringValidator.validateNullOrBlank(username, new UsernameValidationException("닉네임에는 공백이 들어가면 안됩니다."));
        StringValidator.validateLength(USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH, username, new UsernameValidationException("닉네임은 1자 이상 10자 이하여야합니다."));
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}

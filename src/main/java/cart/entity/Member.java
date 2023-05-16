package cart.entity;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import cart.exception.DomainException;
import cart.exception.ExceptionCode;

public class Member {
    private final Long id;
    private final String email;
    private final String password;

    private Member(Long id, String email, String password) {
        this.id = id;
        this.email = validateEmail(email);
        this.password = validatePassword(password);
    }

    public static Member of(Long id, String email, String password) {
        return new Member(id, email, password);
    }

    private String validateEmail(String email) {
        EmailValidator validator = new EmailValidator();
        if (!validator.isValid(email, null)) {
            throw new DomainException(ExceptionCode.INVALID_EMAIL);
        }
        return email;
    }

    private String validatePassword(String password) {
        if (password.isEmpty()) {
            throw new DomainException(ExceptionCode.INVALID_PASSWORD);
        }
        return password;
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
}

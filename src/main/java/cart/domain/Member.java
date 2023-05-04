package cart.domain;

import cart.exception.custom.InvalidPasswordException;

public class Member {

    private Long id;
    private final String email;
    private final String password;

    public Member(String email, String password) {
        this(null, email, password);
    }

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public void validatePassword(String inputPassword) {
        if (!password.equals(inputPassword)) {
            throw new InvalidPasswordException();
        }
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

package cart.entity;

import cart.exception.customexceptions.NotValidDataException;

public class Member {

    private Long id;
    private String email;
    private String name;
    private String password;

    public Member(final Long id, final String email, final String name, final String password) {
        validateEmpty(email);
        validateEmpty(name);
        validateEmpty(password);
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Member(final String email, final String name, final String password) {
        this(null, email, name, password);
    }

    public void validateEmpty(final String value) {
        if (value.trim().isEmpty()) {
            throw new NotValidDataException("값은 공백일 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean matchPassword(final String password) {
        return this.password.equals(password);
    }
}

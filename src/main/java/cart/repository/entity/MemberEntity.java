package cart.repository.entity;

import java.util.Objects;

public class MemberEntity {

    private final Long id;
    private final String name;
    private final String email;
    private final String password;

    public MemberEntity(final Long id, final String name, final String email, final String password) {
        validate(name, email, password);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private void validate(final String name, final String email, final String password) {
        validateName(name);
        validateEmail(email);
        validatePassword(password);
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    private void validateEmail(final String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 공백일 수 없습니다.");
        }
    }

    private void validatePassword(final String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 공백일 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberEntity that = (MemberEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }
}

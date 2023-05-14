package cart.domain;

import java.util.Objects;

public class MemberEntity {

    private final Long id;
    private final Email email;
    private final Password password;

    public MemberEntity(final Long id, final Email email, final Password password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public MemberEntity(final Email email, final Password password) {
        this(null, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmailAddress() {
        return email.getAddress();
    }

    public String getPasswordValue() {
        return password.getValue();
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
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
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}

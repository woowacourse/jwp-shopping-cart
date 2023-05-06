package cart.domain;

import java.util.Objects;

public class MemberEntity {

    private final Long id;
    private final Email email;
    private final Password password;

    public MemberEntity(final Long id, final String address, final String password) {
        this.id = id;
        this.email = new Email(address);
        this.password = new Password(password);
    }

    public MemberEntity(final String address, final String password) {
        this(null, address, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getAddress();
    }

    public String getPassword() {
        return password.getValue();
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

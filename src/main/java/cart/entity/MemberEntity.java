package cart.entity;

import cart.domain.member.Email;
import cart.domain.member.Password;

import java.util.Objects;

public class MemberEntity {

    private final Long id;
    private final Email email;
    private final Password password;

    public MemberEntity(Long id, String email, String password) {
        this.id = id;
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberEntity that = (MemberEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

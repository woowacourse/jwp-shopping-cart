package cart.dto.member;

import cart.domain.member.Email;
import cart.domain.member.Password;
import cart.entity.MemberEntity;

import java.util.Objects;

public class MemberDto {

    private final Long id;
    private final Email email;
    private final Password password;

    public MemberDto(Long id, String email, String password) {
        this.id = id;
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public static MemberDto fromEntity(MemberEntity entity) {
        return new MemberDto(entity.getId(), entity.getEmail(), entity.getPassword());
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
        MemberDto memberDto = (MemberDto) o;
        return Objects.equals(id, memberDto.id) && Objects.equals(email, memberDto.email) && Objects.equals(password, memberDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", email=" + email +
                ", password=" + password +
                '}';
    }
}

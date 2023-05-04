package cart.dto.member;

import java.util.Objects;

public class MemberResponseDto {

    private final Long id;
    private final String email;
    private final String password;

    private MemberResponseDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberResponseDto of(Long id, String email, String password) {
        return new MemberResponseDto(id, email, password);
    }

    public static MemberResponseDto fromDto(MemberDto dto) {
        return new MemberResponseDto(dto.getId(), dto.getEmail(), dto.getPassword());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberResponseDto that = (MemberResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "MemberResponseDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

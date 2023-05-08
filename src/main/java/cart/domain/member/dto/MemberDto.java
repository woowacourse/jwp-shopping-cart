package cart.domain.member.dto;

import cart.domain.member.entity.Member;
import cart.dto.MemberCreateRequest;
import java.util.Objects;

public class MemberDto {

    private final String email;
    private final String password;

    public MemberDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberDto of(final Member member) {
        return new MemberDto(member.getEmail(), member.getPassword());
    }

    public static MemberDto of(final MemberCreateRequest memberCreateRequest) {
        return new MemberDto(memberCreateRequest.getEmail(), memberCreateRequest.getPassword());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberDto memberDto = (MemberDto) o;
        return Objects.equals(getEmail(), memberDto.getEmail()) && Objects.equals(
            getPassword(), memberDto.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

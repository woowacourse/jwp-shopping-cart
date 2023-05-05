package cart.dto;

import cart.domain.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

public class MemberDto {

    private final Long id;
    private final String email;
    private final String password;

    private MemberDto(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberDto from(final Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getPassword());
    }

    public static List<MemberDto> from(final List<Member> memberEntities) {
        return memberEntities.stream()
                .map(MemberDto::from)
                .collect(Collectors.toUnmodifiableList());
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

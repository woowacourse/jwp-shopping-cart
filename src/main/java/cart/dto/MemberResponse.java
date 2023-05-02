package cart.dto;

import java.util.List;
import java.util.stream.Collectors;

public class MemberResponse {

    private final Long id;
    private final String email;
    private final String password;

    public MemberResponse(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberResponse from(final MemberDto memberDto) {
        return new MemberResponse(memberDto.getId(), memberDto.getEmail(), memberDto.getPassword());
    }

    public static List<MemberResponse> from(final List<MemberDto> memberDtos) {
        return memberDtos.stream()
                .map(MemberResponse::from)
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

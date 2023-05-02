package cart.controller.dto;

import cart.domain.dto.MemberDto;

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

    public static List<MemberResponse> mapMembers(List<MemberDto> memberDtos) {
        return memberDtos.stream()
                .map(memberDto -> new MemberResponse(
                        memberDto.getId(),
                        memberDto.getEmail(),
                        memberDto.getPassword())
                ).collect(Collectors.toList());
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

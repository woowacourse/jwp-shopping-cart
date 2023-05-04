package cart.dto.member;

import cart.domain.member.Member;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MembersResponseDto {

    private final List<MemberResponseDto> members;

    private MembersResponseDto(final List<MemberResponseDto> members) {
        this.members = members;
    }

    public static MembersResponseDto from(final List<Member> members) {
        List<MemberResponseDto> membersResponseDto = members.stream()
                .map(MemberResponseDto::from)
                .collect(Collectors.toList());

        return new MembersResponseDto(membersResponseDto);
    }

    public List<MemberResponseDto> getMembers() {
        return Collections.unmodifiableList(this.members);
    }
}

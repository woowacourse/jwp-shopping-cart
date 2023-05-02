package cart.mapper;

import cart.domain.member.Member;
import cart.dto.MemberResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MemberResponseMapper {

    private MemberResponseMapper() {
    }

    public static MemberResponse from(final Member member) {
        return MemberResponse.of(member.getId(), member.getEmail(), member.getPassword());
    }

    public static List<MemberResponse> from(final List<Member> members) {
        return members.stream()
                .map(MemberResponseMapper::from)
                .collect(Collectors.toList());
    }
}

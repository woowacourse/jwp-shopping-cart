package cart.member.mapper;

import cart.member.domain.Member;
import cart.member.dto.MemberResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseMapper {

    public static MemberResponse from(final Member member) {
        return MemberResponse.of(member.getId(), member.getEmail(), member.getPassword());
    }

    public static List<MemberResponse> from(final List<Member> members) {
        return members.stream()
                .map(MemberResponseMapper::from)
                .collect(Collectors.toList());
    }
}

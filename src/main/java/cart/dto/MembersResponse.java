package cart.dto;

import cart.domain.member.Member;
import java.util.List;
import java.util.stream.Collectors;

public class MembersResponse {

    private final List<MemberResponse> members;

    public MembersResponse(List<MemberResponse> members) {
        this.members = members;
    }

    public static MembersResponse of(List<Member> members) {
        List<MemberResponse> responses = members.stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
        return new MembersResponse(responses);
    }
    
    public List<MemberResponse> getMembers() {
        return members;
    }
}

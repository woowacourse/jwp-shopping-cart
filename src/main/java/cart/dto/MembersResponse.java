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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MembersResponse response = (MembersResponse) o;

        return members != null ? members.equals(response.members) : response.members == null;
    }

    @Override
    public int hashCode() {
        return members != null ? members.hashCode() : 0;
    }

    public List<MemberResponse> getMembers() {
        return members;
    }
}

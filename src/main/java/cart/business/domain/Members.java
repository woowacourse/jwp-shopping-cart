package cart.business.domain;

import java.util.List;

public class Members {

    private List<Member> members;

    public Members(List<Member> members) {
        this.members = members;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public Member findMemberById(Integer id) {
        for (Member member : members) {
            if (id == member.getId()) {
                return member;
            }
        }
        throw new IllegalArgumentException("memberId에 해당하는 Member가 없습니다");
    }

    public void removeMember(Member member) {
        members.remove(member);
    }

    public List<Member> getMembers() {
        return members;
    }
}

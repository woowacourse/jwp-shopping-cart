package cart.dao.member;

import cart.domain.member.Member;
import java.util.ArrayList;
import java.util.List;

public class FakeMemberDao implements MemberDao {

    private final List<Member> members = new ArrayList<>();

    public void insert(final Member member) {
        members.add(member);
    }

    @Override
    public List<Member> findAll() {
        return members;
    }
}

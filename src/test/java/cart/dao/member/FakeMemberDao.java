package cart.dao.member;

import cart.domain.member.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeMemberDao implements MemberDao {

    private final List<Member> members = new ArrayList<>();

    public Long insert(final Member member) {
        members.add(member);
        return member.getId();
    }

    @Override
    public List<Member> findAll() {
        return members;
    }

    @Override
    public Optional<Member> findByEmailAndPassword(final String email, final String password) {
        return members.stream()
                .filter(member -> member.getEmail().getValue().equals(email)
                        && member.getPassword().getValue().equals(password))
                .findAny();
    }
}

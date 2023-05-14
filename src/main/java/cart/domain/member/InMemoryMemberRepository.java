package cart.domain.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryMemberRepository implements MemberRepository {

    private final List<Member> members = new ArrayList<>();
    private long serial = 0;

    @Override
    public Long save(Member memberToSave) {
        this.members.add(Member.create(
                getSerial(),
                memberToSave.getEmail(),
                memberToSave.getPassword(),
                memberToSave.getName().orElse(null),
                memberToSave.getPhoneNumber().orElse(null)
        ));
        return this.serial;
    }

    private long getSerial() {
        this.serial += 1;
        return this.serial;
    }

    @Override
    public List<Member> findAll() {
        return this.members;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return this.members.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}

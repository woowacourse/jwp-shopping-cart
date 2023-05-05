package cart.dao;

import cart.domain.Member;
import java.util.List;
import java.util.Optional;

public class H2MemberDao implements Dao<Member> {
    @Override
    public Member insert(final Member entity) {
        return null;
    }

    @Override
    public void update(final Member entity) {

    }

    @Override
    public boolean isExist(final Long id) {
        return false;
    }

    @Override
    public Optional<Member> findById(final Long id) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public void deleteById(final Long id) {

    }
}

package cart.domain.member;

import cart.dao.Dao;
import java.util.Optional;

abstract class MemberDao implements Dao<Member> {

    abstract Optional<Member> findByEmail(String email);

    @Override
    public final Member insert(final Member entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void update(final Member entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean isExist(final Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Member> findById(final Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void deleteById(final Long id) {
        throw new UnsupportedOperationException();
    }
}

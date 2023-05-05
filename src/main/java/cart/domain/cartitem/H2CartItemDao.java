package cart.domain.cartitem;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartItemDao extends CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public H2CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CartItem insert(final CartItem entity) {
        return null;
    }

    @Override
    public boolean isExist(final Long id) {
        return false;
    }

    @Override
    public Optional<CartItem> findById(final Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(final Long id) {

    }

    @Override
    List<CartItem> findByMemberId(final Long id) {
        return null;
    }
}

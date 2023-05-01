package cart.cart.repository;

import cart.cart.entity.Cart;
import cart.cart.exception.CartPersistenceFailedException;
import cart.common.exception.PersistenceExceptionMessages;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCartRepository implements CartRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCartRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Cart save(Cart cart) throws CartPersistenceFailedException {
        String sql = "insert into CART (member_email, product_id) values (:memberEmail, :productId)";

        try {
            int updateCount = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(cart));
            if (updateCount == 0) {
                throw new CartPersistenceFailedException(PersistenceExceptionMessages.CART_SAVING_FAILED);
            }
        } catch (DuplicateKeyException exception) {
            throw new CartPersistenceFailedException(PersistenceExceptionMessages.CART_DUPLICATED_SAVING);
        } catch (DataIntegrityViolationException exception) {
            throw new CartPersistenceFailedException(PersistenceExceptionMessages.CART_SAVING_WITH_INVALID_VALUES, exception);
        }

        return cart;
    }

    @Override
    public List<Cart> findAllByMemberEmail(String memberEmail) {
        String sql = "select * from CART where member_email = :memberEmail";
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("memberEmail", memberEmail),
                (rs, rowNum) -> new Cart(rs.getString("member_email"), rs.getLong("product_id"))
        );
    }

    @Override
    public void deleteByMemberEmailAndProductId(String memberEmail, Long productId) {
        String sql = "delete from CART where member_email = :memberEmail and product_id = :productId";
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("memberEmail", memberEmail)
                .addValue("productId", productId);

        int deletedCount = namedParameterJdbcTemplate.update(sql, paramSource);
        if (deletedCount == 0) {
            throw new CartPersistenceFailedException(PersistenceExceptionMessages.CART_DELETING_FAILED);
        }
    }
}

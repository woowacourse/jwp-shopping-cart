package cart.dao;

import cart.dao.entity.CartEntity;
import cart.dao.entity.CartProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class CartDao {

    private static final boolean DOES_NOT_HAVE_SAME_PRODUCTS = false;
    private static final boolean HAS_SAME_PRODUCTS = true;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CartProductEntity> cartProductEntityRowMapper = (resultSet, rowNum) ->
            new CartProductEntity.Builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getInt("price"))
                    .image(resultSet.getString("image"))
                    .build();

    @Autowired
    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartProductEntity> findProductsByMemberId(final Long memberId) {
        final String query = "SELECT CART.id as id, name, price, image " +
                "FROM cart " +
                "JOIN product ON cart.product_id = product.id " +
                "JOIN member ON cart.member_id = member.id " +
                "WHERE member_id = ?";
        return jdbcTemplate.query(query, cartProductEntityRowMapper, memberId);
    }

    public Long insert(final CartEntity cartEntity) {
        final String query = "INSERT INTO CART (member_id, product_id) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(
                    query, new String[]{"ID"}
            );
            preparedStatement.setLong(1, cartEntity.getMemberId());
            preparedStatement.setLong(2, cartEntity.getProductId());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey().longValue());
    }

    public void deleteProduct(final CartEntity cartEntity) {
        final String query = "DELETE FROM CART WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(query, cartEntity.getMemberId(), cartEntity.getProductId());
    }

    public boolean hasSameProduct(final Long memberId, final Long productId) {
        try {
            final String query = "SELECT id FROM CART WHERE member_id = ? AND product_id = ?";
            jdbcTemplate.queryForObject(query, Long.class, memberId, productId);
            return HAS_SAME_PRODUCTS;
        } catch (EmptyResultDataAccessException exception) {
            return DOES_NOT_HAVE_SAME_PRODUCTS;
        }
    }

    public void deleteProductFromCart(final Long cartId) {
        final String sql = "DELETE FROM CART WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }
}

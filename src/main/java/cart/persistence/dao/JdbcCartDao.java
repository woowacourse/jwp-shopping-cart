package cart.persistence.dao;

import cart.persistence.entity.CartEntity;
import cart.persistence.entity.CartProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<CartEntity> actorRowMapper = (resultSet, rowNum) -> new CartEntity(
            resultSet.getLong("user_id"),
            resultSet.getLong("product_id")
    );

    private final RowMapper<CartProductEntity> cartProductRowMapper = (resultSet, rowNum) -> new CartProductEntity(
            resultSet.getLong("cart_id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image_url")
    );

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CART")
                .usingGeneratedKeyColumns("cart_id");
    }

    @Override
    public Long save(final CartEntity cartEntity) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(cartEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<CartEntity> findById(final Long id) {
        final String sql = "SELECT user_id, product_id FROM cart WHERE cart_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, actorRowMapper, id));
    }

    @Override
    public List<CartEntity> findAll() {
        throw new UnsupportedOperationException("지원되지 않는 기능입니다");
    }

    @Override
    public int update(CartEntity cartEntity) {
        throw new UnsupportedOperationException("지원되지 않는 기능입니다");
    }

    @Override
    public int deleteById(long id) {
        final String sql = "DELETE FROM cart WHERE cart_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<CartProductEntity> findProductsByUser(final String email) {
        final String sql = "SELECT c.cart_id, p.name, p.price, p.image_url\n" +
                "FROM product AS p\n" +
                "JOIN cart AS c ON p.product_id = c.product_id\n" +
                "JOIN user_info AS u ON c.user_id = u.user_id WHERE u.email = ?";
        return jdbcTemplate.query(sql, cartProductRowMapper, email);
    }
}

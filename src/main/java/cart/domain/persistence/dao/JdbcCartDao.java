package cart.domain.persistence.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.domain.persistence.ProductDto;
import cart.domain.persistence.entity.CartEntity;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ProductDto> productDtoRowMapper = (resultSet, rowNum) -> new ProductDto(
        resultSet.getLong("cart_id"),
        resultSet.getString("name"),
        resultSet.getInt("price"),
        resultSet.getString("image_url")
    );

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("cart")
            .usingGeneratedKeyColumns("cart_id");
    }

    @Override
    public long save(final CartEntity cartEntity) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(cartEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public List<ProductDto> findAllByMemberId(final Long memberId) {
        final String sql = "SELECT c.cart_id, p.name, p.price, p.image_url FROM cart AS c "
            + "LEFT JOIN product AS p ON c.product_id = p.product_id "
            + "WHERE member_id = ?";
        return jdbcTemplate.query(sql, productDtoRowMapper, memberId);
    }

    @Override
    public int deleteByCartId(final long cartId) {
        final String sql = "DELETE FROM cart WHERE cart_id = ?";
        return jdbcTemplate.update(sql, cartId);
    }

    @Override
    public int deleteByProductId(final long productId) {
        final String sql = "DELETE FROM cart WHERE product_id = ?";
        return jdbcTemplate.update(sql, productId);
    }
}

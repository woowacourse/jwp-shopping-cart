package cart.repository;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ProductEntity> productEntityRowMapper = (rs, rn) ->
            new ProductEntity(
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getInt("price")
            );

    public CartDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public int create(final CartEntity cartEntity) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(cartEntity);
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    public List<ProductEntity> findProductByUserId(final int userId) {
        final String sql = "select product.name, product.image, product.price from cart" +
                " join product on cart.product_id = product.id" +
                " where cart.user_id = ?";
        return jdbcTemplate.query(sql, productEntityRowMapper, userId);
    }
}

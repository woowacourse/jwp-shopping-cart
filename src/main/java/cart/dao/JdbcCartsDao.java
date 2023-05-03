package cart.dao;

import cart.entity.Product;
import cart.entity.vo.Email;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCartsDao implements CartsDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcCartsDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("carts")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long insert(final Email email, final Product product) {
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_email", email.getValue())
                .addValue("product_id", product.getId());

        return simpleJdbcInsert.execute(parameterSource);
    }

    @Override
    public List<Product> findProductsByUserEmail(final Email userEmail) {
        return null;
    }

    @Override
    public void deleteByProductId(final Email userEmail, final long productId) {

    }
}

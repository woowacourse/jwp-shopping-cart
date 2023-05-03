package cart.entity.item;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DbCartItemDao implements CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public DbCartItemDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingColumns("member_id", "product_id")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public CartItem save(final CartItem cartItem) {
        final BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(cartItem);
        final long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        System.out.println("받은 Id: " + id);
        return new CartItem(id, cartItem.getMemberId(), cartItem.getProductId());
    }

    @Override
    public List<CartItem> findAll() {
        return null;
    }

    @Override
    public List<CartItem> findByMemberId(final long memberId) {
        final String sql = "SELECT * FROM cart WHERE member_id = :member_id";
        final Map<String, Long> parameters = Collections.singletonMap("member_id", memberId);
        return namedParameterJdbcTemplate.query(sql, parameters, (resultSet, rowNum) -> new CartItem(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
        ));
    }
}

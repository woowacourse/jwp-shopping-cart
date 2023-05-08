package cart.dao;

import cart.entity.Cart;
import cart.entity.Product;
import cart.entity.vo.Email;
import cart.exception.TableIdNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcCartDao implements CartDao {

    private static final RowMapper<Cart> cartAddedProductRowMapper = (rs, rowNum) ->
            new Cart(
                    rs.getLong("id"),
                    new Email(rs.getString("user_email")),
                    rs.getLong("product_id")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_added_product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long create(final Cart cart) {
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_email", cart.getUserEmail().getValue())
                .addValue("product_id", cart.getProductId());

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public Cart findById(final long id) {
        final String sql = "SELECT * FROM cart_added_product WHERE cart_added_product.id = ?;";

        try {
            return jdbcTemplate.queryForObject(sql, cartAddedProductRowMapper, id);
        } catch (EmptyResultDataAccessException exception) {
            throw new TableIdNotFoundException("해당 카트 id를 찾을 수 없습니다. 입력된 카트 id : " + id);
        }
    }

    @Override
    public Map<Cart, Product> findProductsByUserEmail(final Email userEmail) {
        final String sql = "SELECT * FROM cart_added_product " +
                "JOIN products " +
                "ON cart_added_product.product_id = products.id  " +
                "WHERE cart_added_product.user_email = ?;";

        final List<Map<String, Object>> sqlResults = jdbcTemplate.queryForList(sql, userEmail.getValue());

        return sqlResults.stream()
                .collect(Collectors.toMap(
                        this::makeCartFromQueryMap,
                        this::makeProductFromQueryMap,
                        (left, right) -> left,
                        LinkedHashMap::new
                ));
    }

    private Cart makeCartFromQueryMap(final Map<String, Object> entry) {
        return new Cart(
                (Long) entry.get("ID"),
                new Email((String) entry.get("USER_EMAIL")),
                (Long) entry.get("PRODUCT_ID")
        );
    }

    private Product makeProductFromQueryMap(final Map<String, Object> entry) {
        return new Product(
                (Long) entry.get("PRODUCT_ID"),
                (String) entry.get("PRODUCT_NAME"),
                (Integer) entry.get("PRODUCT_PRICE"),
                (String) entry.get("PRODUCT_IMAGE")
        );
    }

    @Override
    public void delete(final long id) {
        final String sql = "DELETE FROM cart_added_product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

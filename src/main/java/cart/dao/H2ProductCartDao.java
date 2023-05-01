package cart.dao;

import cart.entity.Member;
import cart.entity.ProductCart;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class H2ProductCartDao implements ProductCartDao {

    private static final RowMapper<ProductCart> productCartRowMapper = (resultSet, rowMapper) -> new ProductCart(
            resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getLong("member_id")
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public H2ProductCartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public ProductCart save(ProductCart productCart) {
        String sql = "INSERT INTO product_cart (member_id, product_id) VALUES (:memberId, :productId)";

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(productCart);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new ProductCart(id, productCart.getProductId(), productCart.getMemberId());
    }

    @Override
    public Optional<ProductCart> findById(Long id) {
        String sql = "SELECT * FROM product_cart WHERE id = :id";
        Map<String, Long> parameter = Collections.singletonMap("id", id);
        return findProduct(sql, parameter);
    }

    private Optional<ProductCart> findProduct(String sql, Map<String, Long> parameter) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, parameter, productCartRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ProductCart> findAllByMember(Member member) {
        String sql = "SELECT * FROM product_cart WHERE member_id = :member_id";
        Map<String, Long> parameter = Collections.singletonMap("member_id", member.getId());
        return namedParameterJdbcTemplate.query(sql, parameter, productCartRowMapper);
    }
}

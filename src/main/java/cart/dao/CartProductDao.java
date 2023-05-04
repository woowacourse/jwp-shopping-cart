package cart.dao;

import cart.dao.dto.CartProductResultMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Collections.singletonMap;

@Repository
public class JdbcCartProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCartProductDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartProductResultMap> findAllByUserId(final Long userId) {
        final String sql = "SELECT c.id, c.product_id, p.name, p.price, p.img_url, " +
                "FROM cart c " +
                "JOIN product p on p.id = c.product_id " +
                "WHERE c.user_id = :userId";

        return jdbcTemplate.query(sql, singletonMap("userId", userId), createCartProdcutResultRowMapper());
    }

    private RowMapper<CartProductResultMap> createCartProdcutResultRowMapper() {
        return (rs, rowNum) -> new CartProductResultMap(
                rs.getLong("id"),
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("img_url")
        );
    }
}

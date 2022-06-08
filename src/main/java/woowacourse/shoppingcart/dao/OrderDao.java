package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addOrders(final Long customerId) {
        final String sql = "INSERT INTO orders (customer_id) VALUES (:customerId)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerId", customerId);
        namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = :customerId";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> rs.getLong("id"));
    }

    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String query = "SELECT EXISTS (SELECT * FROM orders WHERE customer_id = :customerid AND id = :orderid)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerid", customerId);
        parameterSource.addValue("orderid", orderId);
        return namedParameterJdbcTemplate.queryForObject(query, parameterSource, Boolean.class);
    }
}

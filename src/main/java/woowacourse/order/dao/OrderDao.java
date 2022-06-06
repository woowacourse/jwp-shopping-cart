package woowacourse.order.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public OrderDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("orders")
            .usingGeneratedKeyColumns("id");
    }

    public Long addOrders(final Long customerId) {
        final SqlParameterSource params = new MapSqlParameterSource("customer_id", customerId);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    // public List<Long> findOrderIdsByCustomerId(final Long customerId) {
    //     final String sql = "SELECT id FROM orders WHERE customer_id = ? ";
    //     return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    // }
    //
    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String sql = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = :customer_id AND id = :id)";
        final SqlParameterSource params = new MapSqlParameterSource("customer_id", customerId)
            .addValue("id", orderId);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, params, Boolean.class));
    }
}

package woowacourse.order.dao;

import java.util.List;

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

    public Long save(final Long customerId) {
        final SqlParameterSource params = new MapSqlParameterSource("customer_id", customerId);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = :customer_id";
        final SqlParameterSource params = new MapSqlParameterSource("customer_id", customerId);
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong("id"));
    }

    public boolean existsByIdAndCustomerId(final Long customerId, final Long orderId) {
        final String sql = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = :customer_id AND id = :id)";
        final SqlParameterSource params = new MapSqlParameterSource("customer_id", customerId)
            .addValue("id", orderId);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, params, Boolean.class));
    }
}

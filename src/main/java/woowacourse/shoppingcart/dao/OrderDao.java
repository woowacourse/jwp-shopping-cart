package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public OrderDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long save(final Long memberId) {
        final SqlParameterSource parameter = new MapSqlParameterSource("member_id", memberId);
        return simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = ? ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String query = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, orderId);
    }
}

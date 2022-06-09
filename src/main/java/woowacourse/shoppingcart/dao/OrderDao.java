package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrderDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("ORDERS")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Long customerId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customerId);

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM ORDERS WHERE customer_id = ? ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public boolean existByCustomerIdAndOrderId(final Long customerId, final Long orderId) {
        final String query = "SELECT EXISTS(SELECT id FROM ORDERS WHERE customer_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, orderId);
    }
}

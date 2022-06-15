package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addOrders(final Long customerId) {
        final String sql = "INSERT INTO orders (customer_id) VALUES (?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = ? ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public Optional<Long> findOrderIdsByCustomerIdAndOrderId(final Long customerId, final Long orderId) {
        final String query = "SELECT id FROM orders WHERE customer_id = ? AND id = ?";

        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getLong("id"), customerId, orderId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

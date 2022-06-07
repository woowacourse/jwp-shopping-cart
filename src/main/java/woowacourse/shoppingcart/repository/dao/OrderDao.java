package woowacourse.shoppingcart.repository.dao;

import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(final Long customerId) {
        final String query = "INSERT INTO orders (customer_id) VALUES (:customerId)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new MapSqlParameterSource(Map.of("customerId", customerId));
        jdbcTemplate.update(query, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}

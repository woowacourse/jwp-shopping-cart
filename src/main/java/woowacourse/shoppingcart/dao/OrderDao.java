package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Orders;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrderDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Long memberId) {
        final SqlParameterSource parameter = new MapSqlParameterSource("member_id", memberId);
        return simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
    }

    public List<Long> findOrdersIdsByMemberId(final Long memberId) {
        String sql = "SELECT id FROM orders WHERE member_id = :memberId";
        final SqlParameterSource parameter = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, parameter, rowMapper());
    }

    private RowMapper<Long> rowMapper() {
        return (rs, rowNum) -> {
            return rs.getLong("id");
        };
    }

    public boolean isValidOrderId(final Long memberId, final Long orderId) {
        final String sql = "SELECT EXISTS(SELECT * FROM orders WHERE member_id = :memberId AND id = :orderId)";
        final SqlParameterSource parameter = new MapSqlParameterSource("memberId", memberId)
                .addValue("orderId", orderId);
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sql, parameter, Boolean.class));
    }
}

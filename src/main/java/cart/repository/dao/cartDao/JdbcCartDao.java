package cart.repository.dao.cartDao;

import cart.entity.Cart;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcCartDao implements CartDao {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcCartDao(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final Cart cart) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(cart);
        return (Long) simpleJdbcInsert.executeAndReturnKey(source);
    }

    @Override
    public List<Long> findAllProductIdByMemberId(final Long memberId) {
        final String sql = "select product_id from cart where member_id = :memberId";
        final SqlParameterSource source = new MapSqlParameterSource()
                .addValue("memberId", memberId);
        return template.query(sql, source, rowMapper());
    }

    @Override
    public int delete(final Long memberId, final Long productId) {
        final String sql = "delete from cart where member_id = :memberId and product_id = :productId";
        final SqlParameterSource source = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("productId", productId);
        return template.update(sql, source);
    }

    public RowMapper<Long> rowMapper() {
        return ((rs, rowNum) -> {
            long productId = rs.getLong("product_id");
            return Long.valueOf(productId);
        });
    }
}

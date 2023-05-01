package cart.dao;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("cart")
                .usingColumns("member_id", "product_id")
                .usingGeneratedKeyColumns("id");
    }

    public List<CartEntity> findAllByMemberId(final Long memberId) {
        final String sql = "SELECT id, member_id, product_id FROM cart WHERE member_Id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CartEntity(
                rs.getLong("id"),
                rs.getLong("member_id"),
                rs.getLong("product_id")
        ), memberId);
    }

    public Long save(final CartEntity cartEntity) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(cartEntity);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

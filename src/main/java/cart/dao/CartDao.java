package cart.dao;

import cart.domain.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartDao {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper cartRowMapper =
            (rs, rowNum) -> new Cart(
                    rs.getLong(1),
                    rs.getLong(2),
                    rs.getLong(3)
            );

    public CartDao(JdbcTemplate template) {
        this.template = template;
        this.simpleJdbcInsert = new SimpleJdbcInsert(template)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Cart cart) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(cart);
        return simpleJdbcInsert.executeAndReturnKey(source).longValue();
    }

    public List<Cart> findByMemberId(Long memberId) {
        final String sql = "select * from cart where member_id = ?";
        return template.query(sql, cartRowMapper, memberId);
    }

    public void deleteById(Long id) {
        final String sql = "delete from cart where id =?";
        template.update(sql, id);
    }
}

package cart.dao;

import cart.domain.Cart;
import cart.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CartDao {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper cartRowMapper =
            (rs, rowNum) -> new Cart(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getLong("product_id")
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

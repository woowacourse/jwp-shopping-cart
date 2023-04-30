package cart.dao;

import cart.domain.CartProduct;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CartProductDao {

    private static final RowMapper<CartProduct> cartProductMapper =
            (rs, rowNum) -> new CartProduct(rs.getLong(1),
                    rs.getLong(2),
                    rs.getLong(3));

    private final JdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartProductDao(final JdbcTemplate template) {
        this.template = template;
        this.simpleJdbcInsert = new SimpleJdbcInsert(template)
                .withTableName("cart_product")
                .usingGeneratedKeyColumns("id");
    }


    public Long save(final CartProduct cartProduct) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(cartProduct);
        return simpleJdbcInsert.executeAndReturnKey(source)
                .longValue();
    }

    public List<CartProduct> findAllByMemberId(final Long memberId) {
        final String sql = "select * from cart_product where member_id = ?";
        return template.query(sql, cartProductMapper, memberId);
    }

    public void deleteById(final Long id) {
        final String sql = "delete from cart_product where id = ?";
        template.update(sql, id);
    }
}

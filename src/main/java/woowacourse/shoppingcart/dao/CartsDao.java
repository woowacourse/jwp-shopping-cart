package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class CartsDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartsDao(final DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("carts")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Carts carts) {
        final SqlParameterSource parameters = new MapSqlParameterSource("member_id", carts.getMemberId())
                .addValue("product_id", carts.getProduct().getId())
                .addValue("quantity", carts.getQuantity());
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public List<Carts> findCartsByMemberId(final Long id) {
        String sql =
                "SELECT c.id, c.member_id, p.id as product_id, p.name, p.price, p.image_url, c.quantity FROM carts c "
                        + "LEFT JOIN product p "
                        + "ON c.product_id = p.id "
                        + "WHERE member_id = :id";
        final SqlParameterSource parameter = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.query(sql, parameter, joinRowMapper());
    }

    public Carts findCartById(final Long id) {
        String sql = "SELECT c.id, c.member_id, p.id as product_id, p.name, p.price, p.image_url, c.quantity FROM carts c "
                + "LEFT JOIN product p "
                + "ON c.product_id = p.id "
                + "WHERE c.id = :id";
        final SqlParameterSource parameter = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.queryForObject(sql, parameter, joinRowMapper());
    }

    public void delete(final Long id) {
        String sql = "DELETE FROM carts WHERE id = :id";
        final SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, parameter);
    }

    public List<Carts> findCartsByIds(final List<Long> cartIds) {
        String sql = "SELECT c.id, c.member_id, p.id as product_id, p.name, p.price, p.image_url, c.quantity FROM carts c "
                + "LEFT JOIN product p "
                + "ON c.product_id = p.id "
                + "WHERE c.id IN(:ids)";
        final SqlParameterSource parameter = new MapSqlParameterSource("ids", cartIds);

        return namedParameterJdbcTemplate.query(sql, parameter, joinRowMapper());
    }

    private RowMapper<Carts> joinRowMapper() {
        return (rs, rowNum) -> {
            final Long id = rs.getLong("id");
            final Long memberId = rs.getLong("member_id");
            final Long productId = rs.getLong("product_id");
            final String name = rs.getString("name");
            final int price = rs.getInt("price");
            final String imageUrl = rs.getString("image_url");
            final int quantity = rs.getInt("quantity");
            return new Carts(id, memberId, new Product(productId, name, price, imageUrl), quantity);
        };
    }
}

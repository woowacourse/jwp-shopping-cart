package cart.domain.cart.dao;

import cart.domain.cart.entity.Cart;
import cart.domain.member.entity.Member;
import cart.domain.product.entity.Product;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cart save(final Cart cart) {
        final String sql = "INSERT INTO cart(product_id, member_id, created_at, updated_at) VALUES(?,?,?,?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final LocalDateTime now = LocalDateTime.now();
        final Timestamp savedNow = Timestamp.valueOf(now);
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, cart.getProduct().getId());
            ps.setLong(2, cart.getMember().getId());
            ps.setTimestamp(3, savedNow);
            ps.setTimestamp(4, savedNow);
            return ps;
        }, keyHolder);
        return new Cart(getId(keyHolder), cart.getProduct(), cart.getMember(), now, now);
    }

    private long getId(final KeyHolder keyHolder) {
        return Long.parseLong(Objects.requireNonNull(keyHolder.getKeys()).get("id").toString());
    }

    public List<Cart> findByMember(final Member member) {
        final String sql = "SELECT cart.id            AS id,"
            + "       cart.created_at    AS created_at,"
            + "       cart.updated_at    AS updated_at,"
            + "       product.id         AS product_id,"
            + "       product.name       AS product_name,"
            + "       product.image_url  AS product_image_url,"
            + "       product.price      AS product_price,"
            + "       product.created_at AS product_created_at,"
            + "       product.updated_at AS product_updated_at "
            + "FROM cart"
            + "         JOIN product on product.id = cart.product_id"
            + "         JOIN member on member.id = cart.member_id "
            + "WHERE cart.member_id = ?;";
        final RowMapper<Cart> rowMapper = makeRowMapper(member);
        return jdbcTemplate.query(sql, rowMapper, member.getId());
    }

    private RowMapper<Cart> makeRowMapper(final Member member) {
        return (resultSet, rowNum) -> new Cart(resultSet.getLong("id"),
            new Product(
                resultSet.getLong("product_id"),
                resultSet.getString("product_name"),
                resultSet.getInt("product_price"),
                resultSet.getString("product_image_url"),
                resultSet.getTimestamp("product_created_at").toLocalDateTime(),
                resultSet.getTimestamp("product_updated_at").toLocalDateTime()
            ),
            member,
            resultSet.getTimestamp("created_at").toLocalDateTime(),
            resultSet.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    public void deleteById(final Long id) {
        final String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean exists(final Cart cart) {
        final String sql = "SELECT count(*) FROM cart WHERE cart.member_id = ? AND cart.product_id = ?";
        final Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cart.getMember().getId(),
            cart.getProduct().getId());
        return count != 0;
    }
}

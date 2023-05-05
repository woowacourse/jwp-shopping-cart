package cart.dao;

import cart.domain.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Cart> cartRowMapper = (resultSet, rowNumber) -> new Cart(
            resultSet.getLong("id"),
            new User(
                    resultSet.getLong("users_id"),
                    new Email(resultSet.getString("email")),
                    new Password(resultSet.getString("password"))
            ),
            new Item.Builder()
                    .id(resultSet.getLong("items_id"))
                    .name(new Name(resultSet.getString("name")))
                    .imageUrl(new ImageUrl(resultSet.getString("image_url")))
                    .price(new Price(resultSet.getInt("price")))
                    .build()
    );

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TODO: 5/4/23 Long 반환 필요 있을지 
    public void save(final Cart cart) {
        final String sql = "INSERT INTO carts(user_id, item_id) VALUES(?, ?)";
        jdbcTemplate.update(sql, cart.getUser().getId(), cart.getItem().getId());
    }

    public List<Cart> findByUserId(final Long userId) {
        final String sql = "SELECT c.id, u.id as users_id, u.email, u.password, i.id as items_id, i.name, i.image_url, i.price " +
                "FROM carts c " +
                "JOIN users u ON c.user_id = u.id " +
                "JOIN items i ON c.item_id = i.id " +
                "WHERE u.id = ? ";
        return jdbcTemplate.query(sql, cartRowMapper, userId);
    }

    public Optional<Cart> findById(final Long cartId) {
        final String sql = "SELECT c.id, u.id as users_id, u.email, u.password, i.id as items_id, i.name, i.image_url, i.price " +
                "FROM carts c " +
                "JOIN users u ON c.user_id = u.id " +
                "JOIN items i ON c.item_id = i.id " +
                "WHERE c.id = ? ";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, cartRowMapper, cartId));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public void delete(final Long cartId) {
        final String sql = "DELETE FROM carts WHERE id = ? ";
        jdbcTemplate.update(sql, cartId);
    }

}

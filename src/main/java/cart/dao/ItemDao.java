package cart.dao;

import cart.domain.ImageUrl;
import cart.domain.Item;
import cart.domain.Name;
import cart.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemDao {

    private final JdbcTemplate jdbcTemplate;

    public ItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Item> actorRowMapper = (resultSet, rowNumber) -> new Item.Builder()
            .id(resultSet.getLong("id"))
            .name(new Name(resultSet.getString("name")))
            .imageUrl(new ImageUrl(resultSet.getString("image_url")))
            .price(new Price(resultSet.getInt("price")))
            .build();

    public List<Item> findAll() {
        final String sql = "SELECT id, name, image_url, price FROM items ";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Optional<Item> findBy(final Long itemId) {
        final String sql = "SELECT id, name, image_url, price FROM items WHERE id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, actorRowMapper, itemId));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Long save(final Item item) {
        final String sql = "INSERT INTO items(name, image_url, price) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getImageUrl());
            preparedStatement.setInt(3, item.getPrice());
            return preparedStatement;
        }, keyHolder);
        return (Long) keyHolder.getKey();
    }

    public void update(final Item item) {
        final String sql = "UPDATE items SET name = ?, image_url = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, item.getName(), item.getImageUrl(), item.getPrice(), item.getId());
    }

    public void deleteBy(final Long itemId) {
        final String sql = "DELETE FROM items WHERE id = ?";
        jdbcTemplate.update(sql, itemId);
    }
}

package cart.dao;

import cart.domain.ImageUrl;
import cart.domain.Item;
import cart.domain.Name;
import cart.domain.Price;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private final RowMapper<Item> actorRowMapper = (resultSet, rowNumber) -> new Item.Builder()
            .id(resultSet.getLong("id"))
            .name(new Name(resultSet.getString("name")))
            .imageUrl(new ImageUrl(resultSet.getString("image_url")))
            .price(new Price(resultSet.getInt("price")))
            .build();

    public List<Item> findAll() {
        final String sql = "SELECT id, name, image_url, price FROM items ";
        return namedParameterJdbcTemplate.query(sql, actorRowMapper);
    }

    public Optional<Item> findById(final Long itemId) {
        final String sql = "SELECT id, name, image_url, price FROM items WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", itemId);
        try {
            return Optional.of(namedParameterJdbcTemplate.queryForObject(sql, param, actorRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Long save(final Item item) {
        final String sql = "INSERT INTO items(name, image_url, price) VALUES(:name, :imageUrl, :price)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(item);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        return (Long) keyHolder.getKey();
    }

    public void update(final Item item) {
        final String sql = "UPDATE items SET name = :name, image_url = :imageUrl, price = :price WHERE id = :id";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(item);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteBy(final Long itemId) {
        final String sql = "DELETE FROM items WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", itemId);
        namedParameterJdbcTemplate.update(sql, param);
    }
}

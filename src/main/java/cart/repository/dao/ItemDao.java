package cart.repository.dao;

import cart.domain.item.Item;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class ItemDao {

    private final RowMapper<Item> actorRowMapper = (resultSet, rowNum) -> new Item(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ItemDao(JdbcTemplate jdbcTemplate) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ITEMS")
                .usingGeneratedKeyColumns("id");
    }

    public Item insert(Item item) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", item.getName());
        parameters.put("image_url", item.getImageUrl());
        parameters.put("price", item.getPrice());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Item(key.longValue(), item);
    }

    public Optional<Item> findById(Long id) {
        String sql = "SELECT id, name, image_url, price FROM ITEMS WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.query(sql, namedParameters, actorRowMapper)
                .stream()
                .findAny();
    }

    public List<Item> findAll() {
        String sql = "SELECT id, name, image_url, price FROM ITEMS";

        return namedParameterJdbcTemplate.query(sql, actorRowMapper);
    }

    public int update(Item item) {
        String sql = "UPDATE ITEMS SET name = :name, image_url = :image_url, price = :price WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", item.getName())
                .addValue("image_url", item.getImageUrl())
                .addValue("price", item.getPrice())
                .addValue("id", item.getId());

        return namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM ITEMS WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public List<Item> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        String sql = "SELECT id, name, image_url, price FROM ITEMS WHERE id IN (:ids)";
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);

        return namedParameterJdbcTemplate.query(sql, parameters, actorRowMapper);
    }
}

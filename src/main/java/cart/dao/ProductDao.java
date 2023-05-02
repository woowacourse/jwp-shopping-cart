package cart.dao;

import cart.domain.ImageUrl;
import cart.domain.Name;
import cart.domain.Price;
import cart.domain.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            new Name(resultSet.getString("name")),
            new ImageUrl(resultSet.getString("image")),
            new Price(resultSet.getLong("price"))
    );

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Optional<Long> saveAndGetId(final Product product) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName().name());
        parameters.put("image", product.getImage().imageUrl());
        parameters.put("price", product.getPrice().price());

        return Optional.of(jdbcInsert.executeAndReturnKey(parameters).longValue());
    }

    public List<Product> findAll() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void update(final Product product) {
        final String sql = "UPDATE product SET name = ?, image = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                product.getName().name(),
                product.getImage().imageUrl(),
                product.getPrice().price(),
                product.getId());
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<Product> findById(final Long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

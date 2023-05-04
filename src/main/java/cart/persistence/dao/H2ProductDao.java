package cart.persistence.dao;

import cart.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class H2ProductDao implements ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ProductDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Long id1 = rs.getLong("id");
        String name = rs.getString("name");
        String imageUrl = rs.getString("image_url");
        BigDecimal price = rs.getBigDecimal("price");
        return new Product(id1, name, imageUrl, price);
    };

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT id, name, image_url, price FROM product WHERE id = :id";

        var parameterSource = new MapSqlParameterSource("id", id);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameterSource, productRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long insertAndGetKeyHolder(Product product) {
        String sql = "INSERT INTO PRODUCT(name, image_url, price) VALUES(:name, :image_url, :price)";

        var keyHolder = new GeneratedKeyHolder();

        var parameterSource = new MapSqlParameterSource("name", product.getName())
                .addValue("image_url", product.getImageUrl())
                .addValue("price", product.getPrice());

        jdbcTemplate.update(sql, parameterSource, keyHolder);

        return Long.valueOf(keyHolder.getKeys().get("id").toString());
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, image_url, price FROM PRODUCT";

        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE PRODUCT SET id=:id, name=:name, image_url=:image_url, price=:price WHERE id=:id";

        var parameterSource = new MapSqlParameterSource("id", product.getId())
                .addValue("name", product.getName())
                .addValue("image_url", product.getImageUrl())
                .addValue("price", product.getPrice());

        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM PRODUCT WHERE id=:id";

        var parameterSource = new MapSqlParameterSource("id", id);

        jdbcTemplate.update(sql, parameterSource);
    }
}

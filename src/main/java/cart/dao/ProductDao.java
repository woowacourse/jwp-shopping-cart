package cart.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {

    private static final RowMapper<ProductEntity> productRowMapper = (rs, rowNum) -> new ProductEntity(
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getBigDecimal("price")
        );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(ProductEntity productEntity) {
        String sql = "insert into PRODUCT(name, image_url, price) values (:name,:image_url,:price)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", productEntity.getName())
                .addValue("image_url", productEntity.getImageUrl())
                .addValue("price", productEntity.getPrice());

        jdbcTemplate.update(sql, paramSource, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ProductEntity> findAll() {
        String sql = "select * from PRODUCT";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Optional<ProductEntity> findById(long id) {
        String sql = "select * from PRODUCT where product_id = :product_id";

        SqlParameterSource paramSource = new MapSqlParameterSource().addValue("product_id", id);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, paramSource, productRowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateProduct(ProductEntity productEntity) {
        String sql = "update PRODUCT set name = :name, image_url = :image_url, price = :price where product_id = :product_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("product_id", productEntity.getId())
                .addValue("name", productEntity.getName())
                .addValue("image_url", productEntity.getImageUrl())
                .addValue("price", productEntity.getPrice());

        jdbcTemplate.update(sql, paramSource);
    }

    public void deleteProduct(Long productId) {
        String sql = "delete from PRODUCT where product_id = :product_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("product_id", productId);

        jdbcTemplate.update(sql, paramSource);
    }
}

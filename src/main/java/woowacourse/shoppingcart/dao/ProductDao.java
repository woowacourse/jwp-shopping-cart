package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.ProductEntity;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<ProductEntity> ROW_MAPPER = (rs, rownum) -> new ProductEntity(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
    );

    public ProductDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final ProductEntity productEntity) {
        final String query = "INSERT INTO product (name, price, image_url) VALUES (:name, :price, :imageUrl)";
        SqlParameterSource source = new BeanPropertySqlParameterSource(productEntity);
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, source, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public ProductEntity findProductById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = :productId";
            SqlParameterSource source = new MapSqlParameterSource("productId", productId);
            return jdbcTemplate.queryForObject(query, source, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<ProductEntity> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, ROW_MAPPER);
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :productId";
        SqlParameterSource source = new MapSqlParameterSource("productId", productId);
        jdbcTemplate.update(query, source);
    }
}

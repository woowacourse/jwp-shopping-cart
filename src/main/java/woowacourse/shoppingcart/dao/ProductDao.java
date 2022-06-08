package woowacourse.shoppingcart.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.entity.ProductEntity;

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

    public Optional<ProductEntity> findById(final Long productId) {
        final String query = "SELECT id, name, price, image_url FROM product WHERE id = :productId";
        SqlParameterSource source = new MapSqlParameterSource("productId", productId);
        ProductEntity result = DataAccessUtils.singleResult(jdbcTemplate.query(query, source, ROW_MAPPER));
        return Optional.ofNullable(result);
    }

    public List<ProductEntity> findByIds(List<Long> productIds) {
        if (productIds.isEmpty()) {
            return new ArrayList<>();
        }

        final String query = "SELECT id, name, price, image_url FROM product WHERE id IN (:productIds)";
        SqlParameterSource source = new MapSqlParameterSource("productIds", productIds);
        return jdbcTemplate.query(query, source, ROW_MAPPER);
    }

    public List<ProductEntity> findAll() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, ROW_MAPPER);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM product WHERE id = :id)";
        SqlParameterSource source = new MapSqlParameterSource("id", id);
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, source, Boolean.class));
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :productId";
        SqlParameterSource source = new MapSqlParameterSource("productId", productId);
        jdbcTemplate.update(query, source);
    }
}

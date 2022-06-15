package woowacourse.shoppingcart.infra.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.infra.dao.entity.ProductEntity;

@Component
public class JdbcProductDao implements ProductDao {
    private static final RowMapper<ProductEntity> PRODUCT_ENTITY_ROW_MAPPER =
            (rs, rowNum) -> new ProductEntity(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getLong("price"),
                    rs.getString("image_url"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ProductEntity save(final ProductEntity productEntity) {
        final Number number = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(productEntity));
        return productEntity.assignId(number.longValue());
    }

    @Override
    public List<ProductEntity> findAllWithPage(final int page, final int size) {
        final String sql = "SELECT id, name, price, image_url FROM product ORDER BY id ASC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, PRODUCT_ENTITY_ROW_MAPPER, size, (page - 1) * size);
    }

    @Override
    public Optional<ProductEntity> findById(final long id) {
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, PRODUCT_ENTITY_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(final long id) {
        final String sql = "DELETE FROM product WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public long countAll() {
        final String sql = "SELECT COUNT(id) FROM product";

        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}

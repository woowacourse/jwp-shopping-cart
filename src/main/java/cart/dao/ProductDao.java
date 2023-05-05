package cart.dao;

import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private static final RowMapper<ProductEntity> PRODUCT_ENTITY_ROW_MAPPER = (rs, rowNum) -> new ProductEntity(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getString("image_url"),
        rs.getInt("price"),
        rs.getString("description")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product")
            .usingColumns("name", "image_url", "price", "description")
            .usingGeneratedKeyColumns("id");
    }

    public Long save(final ProductEntity productEntity) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Optional<ProductEntity> findById(final Long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        final ProductEntity productEntity = namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(
            sql,
            PRODUCT_ENTITY_ROW_MAPPER,
            id
        );
        return Optional.ofNullable(productEntity);
    }

    public List<ProductEntity> findAllByIdIn(final List<Long> ids) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        final String unionSql = ids.stream()
            .map((id) -> sql)
            .collect(Collectors.joining(" UNION ALL "));
        return namedParameterJdbcTemplate.getJdbcTemplate().query(
            unionSql,
            PRODUCT_ENTITY_ROW_MAPPER,
            ids.toArray()
        );
    }

    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM product";
        return namedParameterJdbcTemplate.query(sql,
            (rs, rowNum) -> new ProductEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("price"),
                rs.getString("description")
            )
        );
    }

    public void update(final ProductEntity productEntity) {
        final String sql = "UPDATE product "
            + "SET name = :name, image_url = :imageUrl, price = :price, description = :description "
            + "WHERE id = :id";
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(productEntity);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        namedParameterJdbcTemplate.getJdbcTemplate().update(sql, id);
    }
}

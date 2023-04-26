package cart.dao;

import cart.entity.product.ProductEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

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

    public void delete(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID값이 null일 수 없습니다.");
        }
        final String sql = "DELETE FROM product WHERE id = ?";
        namedParameterJdbcTemplate.getJdbcTemplate().update(sql, id);
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

    public ProductEntity findById(final Long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        return namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(
            sql,
            (rs, rowNum) -> new ProductEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("price"),
                rs.getString("description")
            ),
            id
        );
    }

    public void update(final ProductEntity productEntity) {
        if (!productEntity.isPersisted()) {
            throw new IllegalArgumentException("한 번도 저장되지 않은 데이터는 수정할 수 없습니다.");
        }
        final String sql = "UPDATE product "
            + "SET name = :name, image_url = :imageUrl, price = :price, description = :description "
            + "WHERE id = :id";
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(productEntity);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
}

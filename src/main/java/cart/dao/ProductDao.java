package cart.dao;

import cart.entity.product.ProductEntity;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
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
}

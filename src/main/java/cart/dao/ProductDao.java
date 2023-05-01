package cart.dao;

import cart.entity.product.ProductEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public Optional<ProductEntity> findById(final Long id) {
        final String sql = "SELECT id, name, image_url, price, description FROM product WHERE id = ?";
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(
                            sql,
                            (rs, rowNum) -> new ProductEntity(
                                    rs.getLong("id"),
                                    rs.getString("name"),
                                    rs.getString("image_url"),
                                    rs.getInt("price"),
                                    rs.getString("description")
                            ),
                            id
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ProductEntity> findAll() {
        final String sql = "SELECT id, name, image_url, price, description FROM product";
        return namedParameterJdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ProductEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("image_url"),
                        rs.getInt("price"),
                        rs.getString("description")
                )
        );
    }

    public Long save(final ProductEntity productEntity) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public void update(final ProductEntity productEntity) {
        final String sql = "UPDATE product "
                + "SET name = :name, image_url = :imageUrl, price = :price, description = :description "
                + "WHERE id = :id";
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(productEntity);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public void delete(final Long productId) {
        final String sql = "DELETE FROM product WHERE id = ?";
        namedParameterJdbcTemplate.getJdbcTemplate().update(sql, productId);
    }
}

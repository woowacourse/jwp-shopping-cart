package cart.persistence.dao;

import cart.persistence.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2ProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<ProductEntity> rowMapper = (resultSet, rowNumber) -> ProductEntity.create(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getLong("price"),
            resultSet.getString("image_url")
    );

    public H2ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long save(ProductEntity productEntity) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        return this.simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "SELECT id, name, price, image_url FROM product";
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(ProductEntity productEntity) {
        final String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        this.jdbcTemplate.update(
                sql,
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImageUrl(),
                productEntity.getId()
        );
    }

    @Override
    public void deleteById(long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        int deletedCount = this.jdbcTemplate.update(sql, id);
        if (deletedCount == 0) {
            throw new IllegalArgumentException("없는 id의 Product 삭제를 요청했습니다.");
        }
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = ?";
        try {
            ProductEntity productEntity = this.jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(productEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

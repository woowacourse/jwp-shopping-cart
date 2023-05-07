package cart.dao;

import cart.domain.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductDao {

    private static final RowMapper<ProductEntity> PRODUCT_ENTITY_ROW_MAPPER = (resultSet, rowNum) -> new ProductEntity(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
    );
    private static final String[] GENERATED_ID_COLUMN = {"id"};
    private static final int MINIMUM_AFFECTED_ROWS = 1;

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, PRODUCT_ENTITY_ROW_MAPPER);
    }

    public Long insert(final ProductEntity productEntity) {
        final String sql = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, productEntity.getName());
            preparedStatement.setInt(2, productEntity.getPrice());
            preparedStatement.setString(3, productEntity.getImage());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int update(final Long id, final ProductEntity productEntity) {
        final String sql = "UPDATE product SET name = ?, price = ?, image = ? WHERE id = ?";
        final int affectedRows = jdbcTemplate.update(
                sql,
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImage(),
                id
        );
        validateAffectedRowsCount(affectedRows);
        return affectedRows;
    }

    private void validateAffectedRowsCount(final int affectedRows) {
        if (affectedRows < MINIMUM_AFFECTED_ROWS) {
            throw new IllegalArgumentException("접근하려는 데이터가 존재하지 않습니다.");
        }
    }

    public int delete(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        final int affectedRows = jdbcTemplate.update(sql, id);
        validateAffectedRowsCount(affectedRows);
        return affectedRows;
    }

    public ProductEntity findById(final Long id) {
        final String sql = "SELECT * from product where id = ?";
        return jdbcTemplate.queryForObject(sql, PRODUCT_ENTITY_ROW_MAPPER, id);
    }
}

package cart.dao;

import cart.dao.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> selectAll() {
        final String sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, getProductRowMapper());
    }

    private RowMapper<ProductEntity> getProductRowMapper() {
        return (resultSet, rowNum) -> new ProductEntity.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getInt("price"))
                .image(resultSet.getString("image"))
                .build();
    }

    public Long insert(final ProductEntity newProductEntity) {
        final String sql = "INSERT INTO PRODUCT (name, price, image) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(
                    sql, new String[]{"ID"}
            );
            preparedStatement.setString(1, newProductEntity.getName());
            preparedStatement.setInt(2, newProductEntity.getPrice());
            preparedStatement.setString(3, newProductEntity.getImage());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey().longValue());
    }

    public int update(final ProductEntity productEntity) {
        final String sql = "UPDATE PRODUCT SET name = ?, price = ?, image = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImage(),
                productEntity.getId()
        );
    }

    public int delete(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Optional<ProductEntity> findById(final Long id) {
        final String sql = "SELECT * from product where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getProductRowMapper(), id));
    }
}

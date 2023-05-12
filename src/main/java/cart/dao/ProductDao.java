package cart.dao;

import cart.dao.entity.ProductEntity;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, rowNum) ->
            new ProductEntity.Builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getInt("price"))
                    .image(resultSet.getString("image"))
                    .build();

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, productEntityRowMapper);
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
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, productEntityRowMapper, id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Optional<ProductEntity> findProductByCartId(Long cartId) {
        final String sql = "SELECT PRODUCT.id as id, PRODUCT.name as name, PRODUCT.price as price, PRODUCT.image as image FROM CART JOIN PRODUCT on PRODUCT.id = CART.product_id WHERE CART.id = ?";
        if (jdbcTemplate.query(sql, productEntityRowMapper, cartId).size() > 0) {
            return Optional.of(jdbcTemplate.query(sql, productEntityRowMapper, cartId).get(0));
        }
        return Optional.empty();
    }
}

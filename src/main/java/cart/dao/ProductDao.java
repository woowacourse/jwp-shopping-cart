package cart.dao;

import cart.dao.entity.ProductEntity;
import cart.domain.Product;
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

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, PRODUCT_ENTITY_ROW_MAPPER);
    }

    public Long insert(final Product product) {
        final String sql = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImage());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int update(final Product product, final Long id) {
        final String sql = "UPDATE product SET name = ?, price = ?, image = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                product.getName(),
                product.getPrice(),
                product.getImage(),
                id
        );
    }

    public int delete(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public ProductEntity findById(final Long id) {
        final String sql = "SELECT * from product where id = ?";
        return jdbcTemplate.queryForObject(sql, PRODUCT_ENTITY_ROW_MAPPER, id);
    }
}

package cart.dao.product;

import cart.entity.product.ImageUrl;
import cart.entity.product.Name;
import cart.entity.product.Price;
import cart.entity.product.Product;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> new Product(
        resultSet.getLong("id"),
        new Name(resultSet.getString("name")),
        new ImageUrl(resultSet.getString("image_url")),
        new Price(resultSet.getInt("price"))
    );

    public ProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertProduct(final Product product) {
        String sql = "INSERT INTO product(name, image_url, price) VALUES(?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getImageUrl());
            ps.setInt(3, product.getPrice());
            return ps;
        }, keyHolder);

        return Long.valueOf(keyHolder.getKeys().get("id").toString());
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, image_url, price FROM product";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Product> findById(final Long id) {
        String sql = "SELECT id, name, image_url, price FROM product WHERE id=?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateProduct(final Long id, final Product product) {
        String sql = "UPDATE product SET name=?, image_url=?, price=? WHERE id=?";
        jdbcTemplate.update(sql,
            product.getName(),
            product.getImageUrl(),
            product.getPrice(),
            id);
    }

    @Override
    public Long deleteProduct(final Long id) {
        String sql = "DELETE FROM product WHERE id=?";
        jdbcTemplate.update(sql, id);
        return id;
    }
}

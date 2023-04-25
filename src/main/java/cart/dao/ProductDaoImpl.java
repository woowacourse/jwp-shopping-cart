package cart.dao;

import cart.dto.ProductRequestDto;
import cart.entity.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> new Product(
        resultSet.getLong("id"),
        resultSet.getString("name"),
        resultSet.getString("image_url"),
        resultSet.getInt("price")
    );


    public ProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertProduct(Product product) {
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
        Product product = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return Optional.of(product);
    }

    @Override
    public Product updateProduct(final Long id, final ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public Long deleteProduct(final Long id) {
        return null;
    }
}

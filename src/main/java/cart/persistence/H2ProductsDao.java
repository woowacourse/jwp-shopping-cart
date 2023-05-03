package cart.persistence;

import cart.domain.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class H2ProductsDao implements ProductsDao {

    private JdbcTemplate jdbcTemplate;

    public H2ProductsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(Product product) {
        String sql = "INSERT INTO PRODUCT(name, price, image_url) VALUES(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getImageUrl());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT id, name, image_url, price FROM PRODUCT WHERE id=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        ), id);
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, image_url, price FROM PRODUCT";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        ));
    }

    @Override
    public Long update(Product product) {
        String sql = "UPDATE PRODUCT SET name=?, price=?, image_url=? WHERE id=?";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getId()
        );

        return product.getId();
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM PRODUCT WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}

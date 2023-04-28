package cart.persistence;

import cart.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class H2ProductDao implements ProductDao {

    private JdbcTemplate jdbcTemplate;

    public H2ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(Product product) {
        String sql = "INSERT INTO PRODUCT(name, image_url, price) VALUES(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, product.getName());
            ps.setString(2, product.getImageUrl());
            ps.setInt(3, product.getPrice());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Product find(Long id) {
        String sql = "SELECT id, name, image_url, price FROM PRODUCT WHERE id=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("image_url"),
                resultSet.getInt("price")
        ), id);
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, image_url, price FROM PRODUCT";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("image_url"),
                resultSet.getInt("price")
        ));
    }

    @Override
    public Long update(Product product) {
        String sql = "UPDATE PRODUCT SET name=?, image_url=?, price=? WHERE id=?";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getImageUrl(),
                product.getPrice(),
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

package cart.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cart.entity.Product;
import cart.entity.ProductRepository;

@Repository
public class DBProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Product product) {
        String sql = "INSERT INTO product (name, imgURL, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getImgUrl());
            preparedStatement.setInt(3, product.getPrice());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT name, imgURL, price FROM product WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
            (resultSet, rowNum) -> Product.of(
                id,
                resultSet.getString("name"),
                resultSet.getString("imgURL"),
                resultSet.getInt("price")),
            id);
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, imgUrl, price FROM product";

        return jdbcTemplate.query(sql,
            (resultSet, rowNum) -> Product.of(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("imgURL"),
                resultSet.getInt("price")));
    }

    @Override
    public void updateById(Product product, Long id) {
        String sql = "UPDATE product SET name = ?, imgUrl = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            product.getName(),
            product.getImgUrl(),
            product.getPrice(),
            id);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

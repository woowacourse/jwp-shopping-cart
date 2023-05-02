package cart.repository;

import cart.domain.Product;
import cart.domain.ProductRepository;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class DBProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProductEntity save(Product product) {
        String sql = "INSERT INTO product (name, imgURL, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getImgUrl());
            preparedStatement.setInt(3, product.getPrice());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new ProductEntity(id, product.getName(), product.getImgUrl(), product.getPrice());
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        String sql = "SELECT name, imgURL, price FROM product WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> Optional.of(new ProductEntity(
                        id,
                        resultSet.getString("name"),
                        resultSet.getString("imgURL"),
                        resultSet.getInt("price"))),
                id);
    }

    @Override
    public List<ProductEntity> findAll() {
        String sql = "SELECT id, name, imgUrl, price FROM product";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new ProductEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("imgURL"),
                        resultSet.getInt("price")));
    }

    @Override
    public void update(ProductEntity productEntity) {
        String sql = "UPDATE product SET name = ?, imgUrl = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                productEntity.getName(),
                productEntity.getImgUrl(),
                productEntity.getPrice(),
                productEntity.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

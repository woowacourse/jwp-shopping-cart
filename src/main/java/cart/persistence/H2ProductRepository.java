package cart.persistence;

import cart.business.domain.product.ProductId;
import cart.business.repository.ProductRepository;
import cart.business.domain.product.Product;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class H2ProductRepository implements ProductRepository {

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String image = resultSet.getString("url");
        Integer price = resultSet.getInt("price");
        return new Product(new ProductId(id), new ProductName(name),
                new ProductImage(image), new ProductPrice(price));
    };

    private final JdbcTemplate jdbcTemplate;

    public H2ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer insert(Product product) {
        final String sql = "INSERT INTO PRODUCT (name, url, price) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getUrl());
            statement.setInt(3, product.getPrice());
            return statement;
        }, keyHolder);
        return keyHolder.getKeyAs(Integer.class);
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        final String sql = "SELECT * FROM PRODUCT WHERE id = (?)";
        return jdbcTemplate.query(sql, productRowMapper, productId)
                .stream()
                .findAny();
    }

    @Override
    public Optional<Product> findByName(String name) {
        final String sql = "SELECT * FROM PRODUCT WHERE name = (?)";
        return jdbcTemplate.query(sql, productRowMapper, name)
                .stream()
                .findAny();
    }

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Product update(Product product) {
        final String sql = "UPDATE PRODUCT SET name = (?), url = (?), price = (?) WHERE id = (?)";
        jdbcTemplate.update(sql, product.getName(), product.getUrl(), product.getPrice(), product.getId());
        return product;
    }

    @Override
    public Product remove(Integer productId) {
        final String sql = "DELETE FROM PRODUCT WHERE id = (?)";
        Optional<Product> product = findById(productId);
        jdbcTemplate.update(sql, productId);
        return product.get();
    } // TODO: 인터페이스에서 remove의 반환타입 Optional로 변경
}

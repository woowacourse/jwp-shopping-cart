package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductDao {
    private static final RowMapper<Product> PRODUCT_MAPPER = (rs, rowNum) -> {
        var id = rs.getLong("id");
        var name = rs.getString("name");
        var price = rs.getInt("price");
        var imageURL = rs.getString("image_url");
        return new Product(id, name, price, imageURL);
    };

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findProductById(Long productId) {
        try {
            final String sql = "SELECT * FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, PRODUCT_MAPPER, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException("[ERROR] 없는 상품입니다.");
        }
    }

    public List<Product> findProducts() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, PRODUCT_MAPPER);
    }

    public boolean isValidId(Long id) {
        String sql = "SELECT * FROM product WHERE exists (SELECT name FROM product WHERE id = ?)";
        return jdbcTemplate.query(sql, PRODUCT_MAPPER, id).size() > 0;
    }


    public List<Product> findProductsByPage(int pageNum, int perPage) {
        String sql = "SELECT * FROM product LIMIT ?, ?";
        return jdbcTemplate.query(sql, PRODUCT_MAPPER, (pageNum - 1) * perPage, perPage);
    }

    public Long countTotal() {
        String sql = "SELECT COUNT(id) FROM product";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}

package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {
    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNum) -> {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
    };

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImageUrl());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Product findById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, PRODUCT_ROW_MAPPER, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException("해당 아이디의 상품이 존재하지 않습니다.");
        }
    }

    public List<Product> findProductsBy(final Long pageNumber, final Long limitCount) {
        final Long startProductIndex = (pageNumber - 1) * limitCount;
        final String query = "SELECT id, name, price, image_url FROM product ORDERS LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER, limitCount, startProductIndex);
    }

    public boolean existProduct(final Product product) {
        String query = "SELECT EXISTS (SELECT id FROM product WHERE name = ? AND price = ? AND image_url = ?)";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(query, Boolean.class, product.getName(), product.getPrice(),
                        product.getImageUrl()));
    }
}

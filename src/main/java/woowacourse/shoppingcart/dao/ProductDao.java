package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Page;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    public static final int FUNCTION_SUCCESS = 1;
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
    );

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImageUrl());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Product> findProductById(final Long productId) {
        final String query = "SELECT id, name, price, image_url FROM product WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, PRODUCT_ROW_MAPPER, productId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }

    public List<Product> findProductsByPage(final Page page) {
        final String query = "SELECT id, name, price, image_url FROM product ORDER BY id Limit ? , ?";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER, page.getBegin(), page.getSize());
    }

    public boolean delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(query, productId) == FUNCTION_SUCCESS;
    }

    public Integer countProducts() {
        final String query = "SELECT count(*) from product";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }
}

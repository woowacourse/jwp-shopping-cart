package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.UnexpectedException;

@Repository
public class ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNumber) ->
        new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image_url"),
            resultSet.getString("description")
        );

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url, is_deleted, description) "
            + "VALUES (?, ?, ?, 0, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                connection.prepareStatement(query, new String[] {"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImageUrl());
            preparedStatement.setString(4, product.getDescription());
            return preparedStatement;
        }, keyHolder);
        return DaoSupporter.getGeneratedId(keyHolder, () -> new UnexpectedException("상품 추가 중 알수없는 오류가 발생했습니다."));
    }

    public Optional<Product> findProductById(final Long productId) {
        try {
            final String query = "SELECT * FROM product WHERE id = ? and is_deleted = 0";
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, PRODUCT_ROW_MAPPER, productId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT * FROM product WHERE is_deleted = 0";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }

    public boolean delete(final Long productId) {
        final String query = "UPDATE product SET is_deleted = 1 WHERE id = ?";
        return DaoSupporter.isUpdated(jdbcTemplate.update(query, productId));
    }
}

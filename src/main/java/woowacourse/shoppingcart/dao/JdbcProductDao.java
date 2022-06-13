package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

@Repository
public class JdbcProductDao implements ProductDao {
    private static final String TABLE_NAME = "product";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String PRICE_COLUMN = "price";
    private static final String STOCK_COLUMN = "stock";
    private static final String IMAGE_URL_COLUMN = "image_url";

    private static final RowMapper<ProductEntity> PRODUCT_ROW_MAPPER = (resultSet, rowNumber) ->
            new ProductEntity(
                    resultSet.getLong(ID_COLUMN),
                    resultSet.getString(NAME_COLUMN),
                    resultSet.getString(DESCRIPTION_COLUMN),
                    resultSet.getInt(PRICE_COLUMN),
                    resultSet.getInt(STOCK_COLUMN),
                    resultSet.getString(IMAGE_URL_COLUMN)
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcProductDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    public Long save(Product product) {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME_COLUMN, product.getName());
        params.put(DESCRIPTION_COLUMN, product.getDescription());
        params.put(PRICE_COLUMN, product.getPrice());
        params.put(STOCK_COLUMN, product.getStock());
        params.put(IMAGE_URL_COLUMN, product.getImageUrl());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Optional<ProductEntity> findById(long productId) {
        String query = "SELECT id, name, description, price, stock, image_url FROM product WHERE id = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, PRODUCT_ROW_MAPPER, productId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ProductEntity> findAll() {
        String query = "SELECT id, name, description, price, stock, image_url FROM product";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }

    public void delete(long productId) {
        String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}

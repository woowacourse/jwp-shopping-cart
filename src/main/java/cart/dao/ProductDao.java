package cart.dao;

import cart.entiy.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, rowNum) -> {
        long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        String image = resultSet.getString("image");
        int price = resultSet.getInt("price");
        return new ProductEntity(productId, name, image, price);
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("product_id");
    }

}

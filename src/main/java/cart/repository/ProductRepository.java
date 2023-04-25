package cart.repository;

import cart.entity.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingColumns("name", "price", "image_url")
                .usingGeneratedKeyColumns("id");
    }

    public long save(String name, int price, String imageUrl) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name", name);
        sqlParameterSource.addValue("price", price);
        sqlParameterSource.addValue("image_url", imageUrl);
        return simpleJdbcInsert.execute(sqlParameterSource);
    }

    public List<ProductEntity> findAll() {
        String sql = "SELECT id, name, price, image_url FROM PRODUCT";
        return jdbcTemplate.query(sql, rs -> {
            List<ProductEntity> productEntities = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                int price = rs.getInt(3);
                String imageUrl = rs.getString(4);
                productEntities.add(new ProductEntity(id, name, price, imageUrl));
            }
            return productEntities;
        });
    }
}

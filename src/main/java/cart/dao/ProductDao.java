package cart.dao;

import cart.dao.entity.ProductEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProductEntity.Builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .imgUrl(rs.getString("img_url"))
                        .price(rs.getInt("price"))
                        .build()
        );
    }
}

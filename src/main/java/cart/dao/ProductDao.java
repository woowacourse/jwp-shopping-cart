package cart.dao;

import cart.dao.entity.ProductEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
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

    public long insert(ProductEntity productEntity) {
        SimpleJdbcInsert jdbcInsert =
                simpleJdbcInsert.withTableName("product").usingGeneratedKeyColumns("id");

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", productEntity.getName())
                .addValue("price", productEntity.getPrice())
                .addValue("img_url", productEntity.getImgUrl());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public void update(ProductEntity productEntity) {
        String sql = "UPDATE product SET name = ?, img_url = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                productEntity.getName(),
                productEntity.getImgUrl(),
                productEntity.getPrice(),
                productEntity.getId());
    }
}

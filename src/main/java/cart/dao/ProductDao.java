package cart.dao;

import cart.dto.entity.ProductEntity;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public int save(ProductEntity productEntity) {
        GeneratedKeyHolder keyholder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO product (name, imgUrl, price) VALUES (:name, :imgUrl, :price)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(productEntity);
        namedParameterJdbcTemplate.update(sql, namedParameters, keyholder, new String[]{"id"});

        return keyholder.getKey().intValue();
    }

    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM product";
        BeanPropertyRowMapper<ProductEntity> mapper = BeanPropertyRowMapper.newInstance(ProductEntity.class);
        return jdbcTemplate.query(sql, mapper);
    }

    public int update(ProductEntity productEntity) {
        final String sql = "UPDATE product SET name=:name, imgUrl=:imgUrl, price=:price WHERE id=:id";
        MapSqlParameterSource mapSqlParameters = new MapSqlParameterSource()
                .addValue("id", productEntity.getId())
                .addValue("name", productEntity.getName())
                .addValue("imgUrl", productEntity.getImgUrl())
                .addValue("price", productEntity.getPrice());
        return namedParameterJdbcTemplate.update(sql, mapSqlParameters);
    }

    public int delete(int id) {
        final String sql = "DELETE FROM product WHERE id=:id";
        MapSqlParameterSource mapSqlParameters = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, mapSqlParameters);
    }
}

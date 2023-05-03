package cart.dao;

import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
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

    public int save(ProductRequest productRequest) {
        GeneratedKeyHolder keyholder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO product (name, imgUrl, price) VALUES (:name, :imgUrl, :price)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(productRequest);
        namedParameterJdbcTemplate.update(sql, namedParameters, keyholder, new String[]{"id"});

        return keyholder.getKey().intValue();
    }

    public List<ProductResponse> findAll() {
        final String sql = "SELECT * FROM product";
        BeanPropertyRowMapper<ProductResponse> mapper = BeanPropertyRowMapper.newInstance(ProductResponse.class);
        return jdbcTemplate.query(sql, mapper);
    }

    public int update(ProductRequest productRequest, int id) {
        final String sql = "UPDATE product SET name=:name, imgUrl=:imgUrl, price=:price WHERE id=:id";
        MapSqlParameterSource mapSqlParameters = new MapSqlParameterSource()
                .addValue("name", productRequest.getName())
                .addValue("imgUrl", productRequest.getImgUrl())
                .addValue("price", productRequest.getPrice())
                .addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, mapSqlParameters);
    }

    public int delete(int id) {
        final String sql = "DELETE FROM product WHERE id=:id";
        MapSqlParameterSource mapSqlParameters = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, mapSqlParameters);
    }
}

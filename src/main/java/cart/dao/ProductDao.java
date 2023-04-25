package cart.dao;

import cart.dto.ProductDto;
import cart.dto.ProductRequest;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public int save(ProductRequest productRequest) {
        GeneratedKeyHolder keyholder = new GeneratedKeyHolder();
        String sql = "INSERT INTO product (name, imgUrl, price) VALUES (:name, :imgUrl, :price)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(productRequest);
        namedParameterJdbcTemplate.update(sql, namedParameters, keyholder, new String[]{"id"});

        return keyholder.getKey().intValue();
    }

    public List<ProductDto> findAll() {
        String sql = "SELECT * FROM product";
        BeanPropertyRowMapper<ProductDto> mapper = BeanPropertyRowMapper.newInstance(ProductDto.class);
        return jdbcTemplate.query(sql, mapper);
    }
}

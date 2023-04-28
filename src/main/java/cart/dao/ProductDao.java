package cart.dao;

import cart.dto.ProductDto;
import cart.dto.ProductAddRequest;
import cart.dto.ProductModifyRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
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
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public int save(ProductAddRequest productAddRequest) {
        GeneratedKeyHolder keyholder = new GeneratedKeyHolder();
        String sql = "INSERT INTO product (name, imgUrl, price) VALUES (:name, :imgUrl, :price)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(productAddRequest);
        namedParameterJdbcTemplate.update(sql, namedParameters, keyholder, new String[]{"id"});

        return keyholder.getKey().intValue();
    }

    public Optional<ProductDto> findById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        BeanPropertyRowMapper<ProductDto> mapper = BeanPropertyRowMapper.newInstance(ProductDto.class);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, mapper, id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }



    public List<ProductDto> findAll() {
        String sql = "SELECT * FROM product";
        BeanPropertyRowMapper<ProductDto> mapper = BeanPropertyRowMapper.newInstance(ProductDto.class);
        return jdbcTemplate.query(sql, mapper);
    }

    public int update(ProductModifyRequest productModifyRequest, int id) {
        String sql = "UPDATE product SET name=:name, imgUrl=:imgUrl, price=:price WHERE id=:id";
        MapSqlParameterSource mapSqlParameters = new MapSqlParameterSource()
                .addValue("name", productModifyRequest.getName())
                .addValue("imgUrl", productModifyRequest.getImgUrl())
                .addValue("price", productModifyRequest.getPrice())
                .addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, mapSqlParameters);
    }

    public int delete(int id) {
        String sql = "DELETE FROM product WHERE id=:id";
        MapSqlParameterSource mapSqlParameters = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, mapSqlParameters);
    }
}

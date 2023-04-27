package cart.dao;

import cart.entity.ProductEntity;
import cart.repository.ProductDto;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductJdbcDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Integer insert(final ProductDto productDto) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productDto);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).intValue();
    }

    @Override
    public void update(final Integer id, final ProductDto productDto) {
        String sql = "UPDATE product SET name= ?, image= ?, price=? WHERE id= ?";
        jdbcTemplate.update(
                sql,
                productDto.getName(),
                productDto.getImage(),
                productDto.getPrice(),
                id
        );
    }

    @Override
    public void deleteById(final Integer id) {
        String sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ProductEntity select(final Integer id) {
        String sql = "select * from product where id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) ->
                        new ProductEntity(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("image"),
                                rs.getLong("price")
                        ),
                id
        );
    }

    @Override
    public List<ProductEntity> findAll() {
        String sql = "select * from product";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new ProductEntity(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("image"),
                                rs.getLong("price")
                        )
        );
    }
}

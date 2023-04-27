package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductJdbcDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<ProductEntity> productEntityRowMapper = (rs, rowNum) ->
            new ProductEntity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getLong("price")
            );

    public ProductJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Integer insert(final ProductEntity productEntity) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).intValue();
    }

    @Override
    public void update(final ProductEntity productEntity) {
        String sql = "UPDATE product SET name= ?, image= ?, price=? WHERE id= ?";
        jdbcTemplate.update(
                sql,
                productEntity.getName(),
                productEntity.getImage(),
                productEntity.getPrice(),
                productEntity.getId()
        );
    }

    @Override
    public void deleteById(final Integer id) {
        String sql = "delete from product where id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("아이디를 찾을 수 없습니다.");
        }
    }

    @Override
    public Optional<ProductEntity> findById(final Integer id) {
        String sql = "select * from product where id = ?";

        try {
            ProductEntity productEntity = jdbcTemplate.queryForObject(sql, productEntityRowMapper, id);
            return Optional.of(productEntity);

        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
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

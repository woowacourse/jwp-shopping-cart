package cart.repository;

import cart.dto.request.ProductCreateDto;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {
    private static final int ITEM_NOT_FOUND = 0;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<ProductEntity> productEntityRowMapper = (rs, rn) ->
            new ProductEntity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getInt("price")
            );

    public ProductDao(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public int create(ProductEntity productEntity) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    public List<ProductEntity> findAll() {
        final String sql = "select * from product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    public void update(final ProductCreateDto productCreateDto, final int id) {
        final String updateSql = "update product set name = ?, image = ?, price = ? where id = ?";
        jdbcTemplate.update(updateSql,
                productCreateDto.getName(),
                productCreateDto.getImage(),
                productCreateDto.getPrice(),
                id
        );
    }

    public void delete(final int id) {
        final String sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean exitingProduct(final int id) {
        final String sql = "select * from product where id = ?";
        final List<Object> findItems = jdbcTemplate.query(sql, (rs, rsnum) -> null, id);
        return findItems.size() != ITEM_NOT_FOUND;
    }

}

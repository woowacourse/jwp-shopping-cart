package cart.repository;

import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import cart.exception.ProductNotFoundException;
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

    public int create(final ProductEntity productEntity) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    public List<ProductEntity> findAll() {
        final String sql = "select * from product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    public void update(final ProductRequestDto productRequestDto, final int id) {
        final String sql = "update product set name = ?, image = ?, price = ? where id = ?";
        final int changedRowCount = jdbcTemplate.update(sql,
                productRequestDto.getName(),
                productRequestDto.getImage(),
                productRequestDto.getPrice(),
                id
        );
        validProductExist(changedRowCount);
    }

    public void delete(final int id) {
        final String sql = "delete from product where id = ?";
        final int changeRowCount = jdbcTemplate.update(sql, id);
        validProductExist(changeRowCount);
    }

    private void validProductExist(final int changedRowCount) {
        if (changedRowCount == 0) {
            throw new ProductNotFoundException();
        }
    }
}

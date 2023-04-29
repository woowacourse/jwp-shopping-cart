package cart.dao;

import cart.domain.Product;
import cart.exception.custom.ResourceNotFoundException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final SimpleJdbcInsert insertActor;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image_url"));

    public ProductDao(DataSource dataSource) {
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long save(Product product) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return insertActor.executeAndReturnKey(parameterSource).longValue();
    }

    public Product findById(Long id) {
        String sql = "select * from product where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException();
        }
    }

    public List<Product> findAll() {
        String sql = "select * from product";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public int deleteById(Long id) {
        String sql = "delete from product where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int updateById(Long id, Product product) {
        String sql = "update product " +
                "set name = ? , price = ?, image_url = ? " +
                "where id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }
}

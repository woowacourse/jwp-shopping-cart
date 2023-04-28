package cart.dao;

import cart.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {

    private static final RowMapper<Product> productRowMapper =
            (rs, rowNum) -> new Product(rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4));

    private final JdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate template) {
        this.template = template;
        this.simpleJdbcInsert = new SimpleJdbcInsert(template)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(source)
                .longValue();
    }

    public Optional<Product> findById(final Long id) {
        final String sql = "select * from product where id = ?";
        try {
            return Optional.ofNullable(template.queryForObject(sql, productRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findAll() {
        final String sql = "select * from product";
        return template.query(sql, productRowMapper);
    }

    public void update(final Product product) {
        final String sql = "update product set name = ?, image_url = ?, price = ?";
        template.update(sql, product.getName(), product.getImageUrl(), product.getPrice());
    }

    public void deleteById(final Long id) {
        final String sql = "delete from product where id = ?";
        template.update(sql, id);
    }
}


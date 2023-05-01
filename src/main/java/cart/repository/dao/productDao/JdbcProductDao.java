package cart.repository.dao.productDao;

import cart.entity.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcProductDao implements ProductDao {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcProductDao(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final Product product) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(product);
        return (Long) simpleJdbcInsert.executeAndReturnKey(source);
    }

    @Override
    public Optional<Product> findById(final Long id) {
        final String sql = "select * from product where id = :id";
        final SqlParameterSource source = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            final Product product = template.queryForObject(sql, source, rowMapper());
            return Optional.of(product);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        final String sql = "select * from product";
        return template.query(sql, rowMapper());
    }

    @Override
    public int update(final Product product) {
        final String sql = "update product set name = :name, image_url = :imageUrl, price = :price where id = :id";
        final SqlParameterSource source = new BeanPropertySqlParameterSource(product);
        return template.update(sql, source);
    }

    @Override
    public int delete(final Long id) {
        final String sql = "delete from product where id = :id";
        final SqlParameterSource source = new MapSqlParameterSource("id", id);
        return template.update(sql, source);
    }

    public RowMapper<Product> rowMapper() {
        return (rs, rowNum) -> {
            final Long id = rs.getLong("id");
            final String name = rs.getString("name");
            final String imageUrl = rs.getString("image_url");
            final int price = rs.getInt("price");
            return new Product(id, name, imageUrl, price);
        };
    }
}

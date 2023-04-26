package cart.repository;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final String name = rs.getString("name");
        final String imageUrl = rs.getString("image_url");
        final int price = rs.getInt("price");
        return new Product(id, name, imageUrl, price);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcProductRepository(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAll() {
        String sql = "select * from product";
        return jdbcTemplate.query(sql, PRODUCT_ROW_MAPPER);
    }

    @Override
    public Product save(final Product product) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);
        final String sql = "insert into product (name, image_url, price) values (:name, :imageUrl, :price)";
        jdbcTemplate.update(sql, sqlParameterSource, keyHolder);
        final long id = keyHolder.getKey().longValue();

        return new Product(id, product.getName(), product.getImageUrl(), product.getPrice());
    }

    @Override
    public void update(final Product product) {
        final String sql = "update product set name=:name, price=:price, image_url=:imageUrl where id=:id";
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);
        final int update = jdbcTemplate.update(sql, sqlParameterSource);
        System.out.println(update);
    }

    @Override
    public Optional<Product> findById(final long id) {
        final String sql = "select * from product where id = :id";
        final MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id);
        final Product product = jdbcTemplate.queryForObject(sql, paramSource, PRODUCT_ROW_MAPPER);
        return Optional.ofNullable(product);
    }

    @Override
    public void deleteById(final long id) {
        final String sql = "delete from product where id = :id";
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(sql, parameterSource);
    }
}

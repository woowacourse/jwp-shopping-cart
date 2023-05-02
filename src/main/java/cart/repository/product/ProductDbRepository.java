package cart.repository.product;

import cart.domain.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDbRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public ProductDbRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, price, img_url FROM product";

        return namedParameterJdbcTemplate.query(sql, getProductRowMapper());
    }

    @Override
    public Optional<Product> findById(final Long id) {
        String sql = "SELECT id, name, price, img_url FROM product WHERE id = :id";

        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource("id", id), getProductRowMapper()).stream()
                .findAny();
    }

    @Override
    public Long add(final Product product) {
        BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(product);
        KeyHolder keyHolder = simpleJdbcInsert.executeAndReturnKeyHolder(source);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public Long update(final Product updateProduct) {
        String sql = "UPDATE product SET name = ?, price = ?, img_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, updateProduct.getName(), updateProduct.getPrice(), updateProduct.getImgUrl(), updateProduct.getId());
        return updateProduct.getId();
    }

    @Override
    public void delete(final Product product) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, product.getId());
    }

    private RowMapper<Product> getProductRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            String imgUrl = rs.getString("img_url");

            return Product.from(id, name, imgUrl, price);
        };
    }
}

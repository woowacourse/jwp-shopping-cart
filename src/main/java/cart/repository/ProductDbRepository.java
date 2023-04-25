package cart.repository;

import cart.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDbRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public ProductDbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("product");
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, price, img_url FROM product";

        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            String imgUrl = rs.getString("img_url");

            return Product.from(id, name, imgUrl, price);
        });
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT id, name, price, img_url FROM product WHERE id = :id";

        Product product = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id),
                (rs, rowNum) -> {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    String imgUrl = rs.getString("img_url");

                    return Product.from(id, name, imgUrl, price);
                });

        return Optional.ofNullable(product);
    }

    @Override
    public void add(Product product) {
        BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(product);
        simpleJdbcInsert.execute(source);
    }

    @Override
    public void update(Product updateProduct) {
        String sql = "UPDATE product SET name = ?, price = ?, img_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, updateProduct.getName(), updateProduct.getPrice(), updateProduct.getImgUrl(), updateProduct.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

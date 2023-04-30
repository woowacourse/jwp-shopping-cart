package cart.dao;

import cart.entity.Product;
import cart.exception.NoSuchProductException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductsDao implements ProductsDao {

    private static final RowMapper<Product> productRowMapper = (rs, rowNum) ->
            new Product(rs.getLong("id"),
                    rs.getString("product_name"),
                    rs.getInt("product_price"),
                    rs.getString("product_image"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcProductsDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long create(final String name, final int price, final String image) {
        final MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("product_name", name)
                .addValue("product_price", price)
                .addValue("product_image", image);

        return simpleJdbcInsert.executeAndReturnKey(parameterMap).longValue();
    }

    @Override
    public List<Product> readAll() {
        final String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Product findById(final long id) {
        final String sql = "SELECT * FROM products WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper, id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchProductException("해당 상품이 없습니다. 입략된 상품 id : " + id);
        }
    }

    @Override
    public void update(final Product product,
                       final String nameToUpdate,
                       final int priceToUpdate,
                       final String imageUrlToUpdate
    ) {
        final String sql = "UPDATE products SET product_name = ?, product_price = ? , product_image = ? where id = ?";
        jdbcTemplate.update(sql,
                nameToUpdate,
                priceToUpdate,
                imageUrlToUpdate,
                product.getId()
        );
        product.setName(nameToUpdate);
        product.setPrice(priceToUpdate);
        product.setImageUrl(imageUrlToUpdate);
    }

    @Override
    public void delete(final long id) {
        final String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

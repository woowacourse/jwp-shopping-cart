package cart.repository.product;

import cart.domain.product.Product;
import cart.entiy.product.ProductEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2ProductRepository implements ProductRepository {

    private final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, rowNum) -> {
        long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        String image = resultSet.getString("image");
        int price = resultSet.getInt("price");
        return new ProductEntity(productId, name, image, price);
    };

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;


    public H2ProductRepository(final JdbcTemplate jdbcTemplate) {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("product_id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save(final Product product) {
        final ProductEntity productEntity = ProductEntity.from(product);
        final Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("product_id", productEntity.getId().getValue());
        parameters.put("name", productEntity.getName());
        parameters.put("image", productEntity.getImage());
        parameters.put("price", productEntity.getPrice());
        final long productId = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)).longValue();
        return new ProductEntity(productId, productEntity).toDomain();
    }

    @Override
    public Product update(final Product product) {
        final ProductEntity productEntity = ProductEntity.from(product);
        final String sql = "UPDATE PRODUCT SET name=?, image=?, price=? WHERE product_id=?";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImage(), productEntity.getPrice(),
                productEntity.getId().getValue());
        return productEntity.toDomain();
    }

    @Override
    public List<Product> find() {
        final String sql = "SELECT product_id, name, image, price FROM PRODUCT";
        return jdbcTemplate.query(sql, productEntityRowMapper)
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(final Long id) {
        final String sql = "SELECT product_id, name, image, price FROM PRODUCT WHERE product_id=?";
        try {
            final ProductEntity result = jdbcTemplate.queryForObject(sql, productEntityRowMapper, id);
            return Optional.of(result).map(ProductEntity::toDomain);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM PRODUCT WHERE product_id=?";
        jdbcTemplate.update(sql, id);
    }
}

package cart.repository.product;

import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.service.request.ProductUpdateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static cart.repository.product.ProductJdbcRepository.Table.*;

@Repository
public class ProductJdbcRepository implements ProductRepository {
    enum Table {
        TABLE("products"),
        ID("id"),
        NAME("name"),
        PRICE("price"),
        IMAGE_URL("image");

        private final String name;

        Table(final String name) {
            this.name = name;
        }
    }
    
    private static final int UPDATED_COUNT = 1;
    private static final int DELETED_COUNT = 1;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE.name)
                .usingGeneratedKeyColumns(ID.name);
    }

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
        return new Product(
                ProductId.from(resultSet.getLong(ID.name)),
                resultSet.getString(NAME.name),
                resultSet.getDouble(PRICE.name),
                resultSet.getString(IMAGE_URL.name)
        );
    };

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Optional<Product> findByProductId(final ProductId productId) {
        final String sql = "SELECT * FROM products WHERE id = ?";
        final Product findProduct = jdbcTemplate.queryForObject(sql, productRowMapper, productId.getId());

        return Optional.ofNullable(findProduct);
    }

    @Override
    public ProductId save(final Product product) {
        final long productId = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource()
                .addValue(NAME.name, product.getName())
                .addValue(PRICE.name, product.getPrice())
                .addValue(IMAGE_URL.name, product.getImage())
        ).longValue();
        return ProductId.from(productId);
    }

    @Override
    public ProductId deleteByProductId(final ProductId productId) {
        final String sql = "DELETE FROM products WHERE id = ?";
        final int deleteCount = jdbcTemplate.update(sql, productId.getId());

        if (deleteCount != DELETED_COUNT) {
            throw new IllegalStateException("상품 삭제 도중 오류가 발생하여 실패하였습니다.");
        }

        return productId;
    }

    @Override
    public ProductId updateByProductId(final ProductId productId, final ProductUpdateRequest request) {
        final String updateSql = "UPDATE products SET name = ?, price = ?, image = ? WHERE id = ?";
        final int updateCount = jdbcTemplate.update(updateSql,
                request.getName(),
                request.getPrice(),
                request.getImage(),
                productId.getId());

        if (updateCount != UPDATED_COUNT) {
            throw new IllegalStateException("상품 갱신 도충 오류가 발생하여 실패하였습니다.");
        }

        return productId;
    }
}

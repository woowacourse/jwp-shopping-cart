package cart.repository;

import cart.domain.Product;
import cart.service.request.ProductCreateRequest;
import cart.service.request.ProductUpdateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductJdbcRepository implements ProductRepository {
    private static final int UPDATED_COUNT = 1;
    private static final int DELETED_COUNT = 1;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductJdbcRepository(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getString("image")
        );
    };

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Optional<Product> findByProductId(final long productId) {
        final String sql = "SELECT * FROM products WHERE id = ?";
        final Product findProduct = jdbcTemplate.queryForObject(sql, productRowMapper, productId);

        return Optional.ofNullable(findProduct);
    }

    @Override
    public long save(final ProductCreateRequest request) {
        return simpleJdbcInsert.execute(new MapSqlParameterSource()
                .addValue("name", request.getName())
                .addValue("price", request.getPrice())
                .addValue("image", request.getImage())
        );
    }

    @Override
    public long deleteByProductId(final long productId) {
        final String sql = "DELETE FROM products WHERE id = ?";
        final int deleteCount = jdbcTemplate.update(sql, productId);

        if (deleteCount != DELETED_COUNT) {
            throw new IllegalStateException("상품 삭제 도중 오류가 발생하여 실패하였습니다.");
        }

        return productId;
    }

    @Override
    public long updateByProductId(final long productId, final ProductUpdateRequest request) {
        final String updateSql = "UPDATE products SET name = ?, price = ?, image = ? WHERE id = ?";
        final int updateCount = jdbcTemplate.update(updateSql, request.getName(), request.getPrice(), request.getImage(),
                productId);

        if (updateCount != UPDATED_COUNT) {
            throw new IllegalStateException("상품 갱신 도충 오류가 발생하여 실패하였습니다.");
        }

        return productId;
    }
}

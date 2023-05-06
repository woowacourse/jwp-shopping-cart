package cart.persistence;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2ProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Product> rowMapper = (resultSet, rowNumber) -> Product.create(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getLong("price"),
            resultSet.getString("image_url")
    );

    public H2ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long save(final Product product) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return id;
    }

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT id, name, price, image_url FROM product";
        List<Product> products = jdbcTemplate.query(sql, rowMapper);
        return products;
    }

    @Override
    public void update(Product productToUpdate) {
        final String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                productToUpdate.getName(),
                productToUpdate.getPrice(),
                productToUpdate.getImageUrl(),
                productToUpdate.getId()
        );
    }

    @Override
    public void deleteById(long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        int deletedCount = jdbcTemplate.update(sql, id);
        if (deletedCount == 0) {
            throw new IllegalArgumentException("없는 id의 Product 삭제를 요청했습니다.");
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

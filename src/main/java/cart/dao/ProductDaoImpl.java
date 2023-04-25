package cart.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM PRODUCT";

        return jdbcTemplate.query(sql, productEntityRowMapper());
    }

    private RowMapper<ProductEntity> productEntityRowMapper() {
        return (rs, rowNum) -> new ProductEntity(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getInt("price")
        );
    }

    @Override
    public void insert(ProductEntity productEntity) {
        final String sql = "INSERT INTO PRODUCT(name, image, price) values (?, ?, ?)";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    @Override
    public Optional<ProductEntity> findById(final int id) {
        final String sql = "SELECT * FROM PRODUCT WHERE id = ?";
        ProductEntity productEntity = jdbcTemplate.queryForObject(sql, productEntityRowMapper(), id);
        if (productEntity == null) {
            return Optional.empty();
        }
        return Optional.of(productEntity);
    }

    @Override
    public void update(final ProductEntity updatedEntity) {
        final String sql = "UPDATE PRODUCT SET name = ?, image = ?, price = ? WHERE id = ?";

        jdbcTemplate.update(sql, updatedEntity.getName(), updatedEntity.getImage(), updatedEntity.getPrice(),
                updatedEntity.getId());
    }
}

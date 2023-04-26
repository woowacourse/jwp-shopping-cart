package cart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    public int insert(ProductEntity productEntity) {
        final String sql = "INSERT INTO PRODUCT (name, image, price) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    sql, new String[]{"id"});
            pstmt.setString(1, productEntity.getName());
            pstmt.setString(2, productEntity.getImage());
            pstmt.setInt(3, productEntity.getPrice());
            return pstmt;
        }, keyHolder);
        return keyHolder.getKey().intValue();
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

    @Override
    public void delete(final int id) {
        final String sql = "DELETE FROM PRODUCT WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

}

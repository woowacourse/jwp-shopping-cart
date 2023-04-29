package cart.persistence;

import cart.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Statement;
import java.util.List;

@Repository
public class H2ProductDao implements ProductDao {

    private JdbcTemplate jdbcTemplate;

    public H2ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Long id1 = rs.getLong("id");
        String name = rs.getString("name");
        String imageUrl = rs.getString("image_url");
        BigDecimal price = rs.getBigDecimal("price");
        return new Product(id1, name, imageUrl, price);
    };

    @Override
    public Product findById(Long id) {
        String sql = "SELECT id, name, image_url, price FROM product WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, productRowMapper, id);
    }

    @Override
    public Long insertAndGetKeyHolder(Product product) {
        String sql = "INSERT INTO PRODUCT(name, image_url, price) VALUES(?, ?, ?)";

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getImageUrl());
            ps.setBigDecimal(3, product.getPrice());
            return ps;
        }, keyHolder);

        return Long.valueOf(keyHolder.getKeys().get("id").toString());
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, image_url, price FROM PRODUCT";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getBigDecimal("price")
        ));
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE PRODUCT SET id=?, name=?, image_url=?, price=? WHERE id=?";
        jdbcTemplate.update(sql,
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice(),
                product.getId()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM PRODUCT WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}

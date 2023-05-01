package cart.dao;

import cart.entity.Product;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> new Product.Builder()
            .id(resultSet.getInt("id"))
            .price(resultSet.getInt("price"))
            .name(resultSet.getString("name"))
            .imageUrl(resultSet.getString("image_url"))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> selectAll() {
        String sqlForSelectAll = "SELECT * FROM Product";
        return jdbcTemplate.query(sqlForSelectAll, productRowMapper);
    }

    public int save(Product product) {
        String sqlForSave = "INSERT INTO Product (name, price, image_url) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlForSave, new String[] { "id" });
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getImageUrl());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void deleteById(int id) {
        String sqlForDeleteById = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sqlForDeleteById, id);
    }

    public void updateById(int id, Product product) {
        String sqlForUpdateById = "UPDATE Product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sqlForUpdateById, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }

    public Optional<Product> findById(int id) {
        String sqlForFindById = "SELECT * FROM Product WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sqlForFindById, productRowMapper, id);
        
        if (products.isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(products.get(0));
    }

}

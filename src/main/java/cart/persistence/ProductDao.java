package cart.persistence;

import cart.business.domain.Product;
import cart.entity.ProductEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
@Primary
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer insert(Product product) {
        String sql = "INSERT INTO PRODUCT (name, url, price) values(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, product.getName());
            ps.setString(2, product.getUrl());
            ps.setInt(3, product.getPrice());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<ProductEntity> findAll() {
        String sql = "SELECT * FROM PRODUCT";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String url = resultSet.getString("url");
                    int price = resultSet.getInt("price");

                    return new ProductEntity(id, name, url, price);
                });
    }

    public Integer update(Integer id, Product product) {
        String sql = "UPDATE PRODUCT SET name = ?, url = ?, price = ? WHERE id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getUrl(), product.getPrice(), id);
    }

    public Integer remove(Integer id) {
        final var query = "DELETE FROM PRODUCT WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }

//    public void findSameProductExist(ProductEntity product) {
//        final var query = "SELECT COUNT(*) FROM PRODUCT WHERE name = ? AND url = ? AND price = ?";
//        int count = jdbcTemplate.queryForObject(query, Integer.class, product.getName(), product.getUrl(), product.getPrice());
//
//        if (count > 0) {
//            throw new IllegalArgumentException("같은 상품이 존재합니다.");
//        }
//    }
}

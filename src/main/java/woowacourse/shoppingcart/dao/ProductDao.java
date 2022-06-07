package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.product.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getProducts() {
        final String sql = "select id, name, price, thumbnail from product";
        return jdbcTemplate.query(sql, new ProductMapper());
    }

    private static class ProductMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    new ProductId(rs.getInt("id")),
                    new Name(rs.getString("name")),
                    new Price(rs.getInt("price")),
                    new Thumbnail(rs.getString("thumbnail"))
                    );
        }
    }
}

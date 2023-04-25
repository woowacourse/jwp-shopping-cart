package cart.dao;

import cart.domain.Product;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {

    private static final RowMapper<Product> productRowMapper =
            (rs, rowNum) -> new Product(rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4));

    private final JdbcTemplate template;

    public ProductDao(final JdbcTemplate template) {
        this.template = template;
    }

    public Long save(final Product product) {
        final String sql = "insert into product (name, image_url, price) values (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getImageUrl());
            preparedStatement.setInt(3, product.getPrice());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Product> findById(final Long id) {
        final String sql = "select * from product where id = ?";
        try {
            return Optional.ofNullable(template.queryForObject(sql, productRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findAll() {
        final String sql = "select * from product";
        return template.query(sql, productRowMapper);
    }

    public void update(final Product product) {
        final String sql = "update product set name = ?, image_url = ?, price = ?";
        template.update(sql, product.getName(), product.getImageUrl(), product.getPrice());
    }

    public void deleteById(final Long id) {
        final String sql = "delete from product where id = ?";
        template.update(sql, id);
    }
}


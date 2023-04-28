package cart.domain.product.dao;

import cart.domain.product.entity.Product;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> new Product(
        resultSet.getLong("id"),
        resultSet.getString("name"),
        resultSet.getInt("price"),
        resultSet.getString("image_url"),
        resultSet.getTimestamp("created_at").toLocalDateTime(),
        resultSet.getTimestamp("updated_at").toLocalDateTime()
    );

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product add(final Product product) {
        final String sql = "INSERT INTO product(name, price, image_url, created_at, updated_at) VALUES(?,?,?,?,?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final LocalDateTime now = LocalDateTime.now();
        final Timestamp savedNow = Timestamp.valueOf(now);
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getImageUrl());
            ps.setTimestamp(4, savedNow);
            ps.setTimestamp(5, savedNow);
            return ps;
        }, keyHolder);
        return new Product(getId(keyHolder), product.getName(), product.getPrice(),
            product.getImageUrl(), now, now);
    }

    private long getId(final KeyHolder keyHolder) {
        return Long.parseLong(Objects.requireNonNull(keyHolder.getKeys()).get("id").toString());
    }

    public List<Product> findAll() {
        final String sql = "SELECT id, name, image_url, price, created_at, updated_at FROM product";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public int update(final Product product) {
        final String sql = "UPDATE product SET name = ?, image_url = ?, price = ?, updated_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
            product.getName(),
            product.getImageUrl(),
            product.getPrice(),
            Timestamp.valueOf(LocalDateTime.now()),
            product.getId()
        );
    }

    public int delete(final Long id) {
        final String sql = "DELETE FROM product WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }
}

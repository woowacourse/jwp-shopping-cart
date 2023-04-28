package cart.domain.product.dao;

import cart.domain.product.entity.Product;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

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
}

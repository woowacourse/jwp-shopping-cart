package cart.repository.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CARTS")
                .usingGeneratedKeyColumns("id");
    }

    public Long insert(String email) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("user_email", email);

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return key.longValue();
    }

    public Optional<Long> findByEmail(String email) {
        String sql = "SELECT id FROM CARTS WHERE user_email = ?";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> resultSet.getLong("id"), email)
                .stream()
                .findAny();
    }
}

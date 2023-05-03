package cart.repository;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public int create(final CartEntity cartEntity) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(cartEntity);
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }
}

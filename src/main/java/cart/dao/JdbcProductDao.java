package cart.dao;

import cart.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductDao implements ProductDao {
    private SimpleJdbcInsert insertActor;

    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long insert(final Product product) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return insertActor.executeAndReturnKey(parameters).longValue();
    }
}

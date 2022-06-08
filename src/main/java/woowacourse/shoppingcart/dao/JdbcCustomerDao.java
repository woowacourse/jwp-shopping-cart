package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.entity.CustomerEntity;

@Repository
public class JdbcCustomerDao implements CustomerDao {
    private static final String TABLE_NAME = "customer";
    private static final String ID_COLUMN = "id";

    private static final RowMapper<CustomerEntity> CUSTOMER_ENTITY_ROW_MAPPER = (rs, rowNum) -> new CustomerEntity(
            rs.getInt("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("profile_image_url"),
            rs.getBoolean("terms")
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcCustomerDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public int save(Customer customer) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", customer.getEmail().getValue());
        params.put("password", customer.getPassword().getValue());
        params.put("profile_image_url", customer.getProfileImageUrl().getValue());
        params.put("terms", customer.isTerms());

        return jdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public CustomerEntity findById(int id) {
        String sql = "SELECT id, email, password, profile_image_url, terms FROM customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, CUSTOMER_ENTITY_ROW_MAPPER, id);
    }

    @Override
    public CustomerEntity findByEmail(String email) {
        String sql = "SELECT id, email, password, profile_image_url, terms FROM customer WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, CUSTOMER_ENTITY_ROW_MAPPER, email);
    }

    @Override
    public void update(int id, Customer customer) {
        String sql = "UPDATE customer SET password = ?, profile_image_url = ?, terms = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getPassword().getValue(), customer.getProfileImageUrl().getValue(),
                customer.isTerms(), id);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean hasEmail(String email) {
        String sql = "SELECT EXISTS(SELECT * FROM customer WHERE email = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, email));

    }
}

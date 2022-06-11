package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.notfound.CustomerNotFoundException;

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
    public int save(CustomerEntity customerEntity) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", customerEntity.getEmail());
        params.put("password", customerEntity.getPassword());
        params.put("profile_image_url", customerEntity.getProfileImageUrl());
        params.put("terms", customerEntity.isTerms());

        return jdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public CustomerEntity findById(long id) {
        String sql = "SELECT id, email, password, profile_image_url, terms FROM customer WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, CUSTOMER_ENTITY_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public CustomerEntity findByEmail(String email) {
        String sql = "SELECT id, email, password, profile_image_url, terms FROM customer WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sql, CUSTOMER_ENTITY_ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public void update(long id, CustomerEntity customerEntity) {
        String sql = "UPDATE customer SET password = ?, profile_image_url = ?, terms = ? WHERE id = ?";
        jdbcTemplate.update(sql, customerEntity.getPassword(), customerEntity.getProfileImageUrl(),
                customerEntity.isTerms(), id);
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(long id) {
        String sql = "SELECT EXISTS(SELECT * FROM customer WHERE id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS(SELECT * FROM customer WHERE email = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, email));
    }
}

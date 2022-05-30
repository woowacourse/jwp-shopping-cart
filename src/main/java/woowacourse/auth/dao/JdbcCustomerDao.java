package woowacourse.auth.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.customer.Email;
import woowacourse.auth.domain.customer.Id;
import woowacourse.auth.entity.CustomerEntity;

@Repository
public class JdbcCustomerDao implements CustomerDao {
    private static final String TABLE_NAME = "CUSTOMER";
    private static final String ID_COLUMN = "id";

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
    public CustomerEntity findById(int id) {
        String sql = "SELECT id, email, password, profile_image_url, terms FROM CUSTOMER WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new CustomerEntity(
                                rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("profile_image_url"),
                                rs.getBoolean("terms")
                        ),
                id
        );
    }

    @Override
    public CustomerEntity findByEmail(Email email) {
        return null;
    }


    @Override
    public void update(CustomerEntity customerEntity) {

    }

    @Override
    public void delete(Id id) {

    }
}

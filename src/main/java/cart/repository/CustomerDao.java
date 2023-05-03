package cart.repository;

import cart.entity.CustomerEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomerEntity> findAll() {
        final String sql = "select * from customer";
        return jdbcTemplate.query(sql, getCustomerMapper());
    }

    private RowMapper<CustomerEntity> getCustomerMapper() {
        return (rs, cn) -> new CustomerEntity(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"));
    }
}

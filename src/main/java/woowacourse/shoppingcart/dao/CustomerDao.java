package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> new Customer(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("nickname")
    );

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Customer customer) {
        SqlParameterSource sqlParameter = new MapSqlParameterSource()
                .addValue("email", customer.getEmail())
                .addValue("password", customer.getPassword())
                .addValue("nickname", customer.getNickname());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameter).longValue();
    }

    public boolean existByEmail(final String email) {
        String sql = "SELECT exists (SELECT * FROM customer WHERE email=:email)";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("email", email);
        Long count = template.queryForObject(sql, nameParameters, Long.class);
        return count != 0L;
    }

    public boolean existByNickname(final String nickname) {
        String sql = "SELECT exists (SELECT * FROM customer WHERE nickname=:nickname)";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("nickname", nickname);
        Long count = template.queryForObject(sql, nameParameters, Long.class);
        return count != 0;
    }

    public Optional<Customer> findById(final Long id) {
        String query = "SELECT id, email, password, nickname FROM customer WHERE id=:id";
        SqlParameterSource nameParameters = new MapSqlParameterSource("id", id);

        try {
            Customer customer = template.queryForObject(query, nameParameters, CUSTOMER_ROW_MAPPER);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        String query = "SELECT id, email, password, nickname FROM customer WHERE email=:email AND password=:password";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("email", email)
                .addValue("password", password);
        try {
            Customer customer = template.queryForObject(query, nameParameters, CUSTOMER_ROW_MAPPER);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existByNicknameExcludedId(Long id, String nickname) {
        String query = "SELECT EXISTS (SELECT * FROM customer WHERE id != :id AND nickname=:nickname)";

        MapSqlParameterSource nameParameters = new MapSqlParameterSource("id", id)
                .addValue("nickname", nickname);
        int count = template.queryForObject(query, nameParameters, Integer.class);
        return count != 0;
    }

    public void update(Customer customer) {
        String query = "UPDATE customer SET password=:password, nickname=:nickname where id=:id";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("password", customer.getPassword())
                .addValue("nickname", customer.getNickname())
                .addValue("id", customer.getId());
        template.update(query, nameParameters);
    }

    public void delete(Long id) {
        String query = "DELETE FROM customer WHERE id=:id";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("id", id);
        template.update(query, nameParameters);
    }
}

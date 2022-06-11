package woowacourse.shoppingcart.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) ->
            new Customer(
                    rs.getLong("id"),
                    new Account(rs.getString("account")),
                    new Nickname(rs.getString("nickname")),
                    new EncodedPassword(rs.getString("password")),
                    new Address(rs.getString("address")),
                    new PhoneNumber(rs.getString("phone_number"))
            );

    public CustomerDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Customer save(final Customer customer) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("account", customer.getAccount().getValue());
        parameters.put("nickname", customer.getNickname().getValue());
        parameters.put("password", customer.getPassword().getValue());
        parameters.put("address", customer.getAddress().getValue());
        parameters.put("phone_number", customer.getPhoneNumber().getValue());

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Customer(number.longValue(), customer.getAccount(), customer.getNickname(), customer.getPassword(), customer.getAddress(), customer.getPhoneNumber());
    }

    public Optional<Customer> findByAccount(final String account) {
        final String sql = "SELECT id, account, nickname, password, address, phone_number " +
                "FROM customer WHERE account=:account";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("account", account);

        final List<Customer> query = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), customerRowMapper);
        return Optional.ofNullable(DataAccessUtils.singleResult(query));
    }

    public Optional<Customer> findById(final long customerId) {
        final String sql = "SELECT id, account, nickname, password, address, phone_number " +
                "FROM customer WHERE id=:customerId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);

        final List<Customer> result = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), customerRowMapper);
        return Optional.ofNullable(DataAccessUtils.singleResult(result));
    }

    public int update(final long id, final String nickname, final String address, final String phoneNumber) {
        final String sql = "UPDATE customer SET nickname=:nickname, address=:address, phone_number=:phone_number " +
                "WHERE id=:id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("nickname", nickname);
        parameters.put("address", address);
        parameters.put("phone_number", phoneNumber);

        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(parameters));
    }


    public int deleteById(long id) {
        final String sql = "DELETE FROM customer WHERE id=:id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(parameters));
    }
}

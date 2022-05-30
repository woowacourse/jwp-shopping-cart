package woowacourse.auth.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.auth.domain.Customer;

@Repository
public class CustomerDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public CustomerDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("customer")
			.usingGeneratedKeyColumns("id");
	}

	public Customer save(Customer customer) {
		long id = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(customer))
			.longValue();
		return new Customer(id, customer.getEmail(), customer.getPassword(), customer.getNickname());
	}

	public Boolean existByEmail(String email) {
		String sql = "select exists (select * from customer where email = :email)";
		return jdbcTemplate.queryForObject(sql, Map.of("email", email), Boolean.class);
	}

	public Long findIdByUserName(String nickname) {
		return null;
	}
}

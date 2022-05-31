package woowacourse.auth.dao;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
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

	public Optional<Customer> findByEmail(String email) {
		String sql = "select * from customer where email = :email";
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject(sql, Map.of("email", email), getCustomerMapper())
			);
		} catch (EmptyResultDataAccessException exception) {
			return Optional.empty();
		}
	}

	private RowMapper<Customer> getCustomerMapper() {
		return (rs, rowNum) -> new Customer(
			rs.getLong("id"),
			rs.getString("email"),
			rs.getString("password"),
			rs.getString("nickname"));
	}

	public void delete(Long id) {
		String sql = "DELETE FROM customer WHERE id = :id";
		jdbcTemplate.update(sql, Map.of("id", id));
	}

	public void update(Customer customer) {
		String sql = "update customer set "
			+ "email = :email, "
			+ "nickname = :nickname, "
			+ "password = :password "
			+ "where id = :id";
		if (jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(customer)) == 0) {
			throw new NoSuchElementException("수정하려는 Customer가 없습니다.");
		}
	}
}

package cart.dao.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.user.User;

@Component
public class UserDao {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
		User user = new User(
			rs.getString("email"),
			rs.getString("password")
		);
		return user;
	};

	@Autowired
	public UserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<User> findAll() {
		String sql = "SELECT * FROM USERS";

		return jdbcTemplate.query(sql, userRowMapper);
	}

	@Transactional
	public Optional<User> findUserByEmail(final String email) {
		final String sql = "SELECT * FROM USERS where email = ?";

		try {
			final User user = jdbcTemplate.queryForObject(sql, userRowMapper, email);
			return Optional.ofNullable(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

}

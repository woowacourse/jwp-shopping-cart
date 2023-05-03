package cart.dao.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cart.domain.user.User;

@Component
public class UserDao {

	private final JdbcTemplate jdbcTemplate;

	private RowMapper<User> userRowMapper = (rs, rowNum) -> {
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

}

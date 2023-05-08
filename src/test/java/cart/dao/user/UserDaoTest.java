package cart.dao.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;

import cart.domain.user.User;

@JdbcTest
@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDaoTest {

	private UserDao userDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
		rs.getLong("id"),
		rs.getString("email"),
		rs.getString("password")
	);

	@BeforeEach
	public void setTest() {
		userDao = new UserDao(jdbcTemplate);
	}

	@Test
	void findAllTest() {
		//given
		final List<User> users = jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);

		//when
		final List<User> result = userDao.findAll();

		//then
		assertThat(result).hasSize(users.size());
	}

	@Test
	void findUserByEmailTest() {
		//given
		final List<User> users = jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);
		final String email = users.get(0).getEmail();

		//when
		final Optional<User> result = userDao.findUserByEmail(email);

		//then
		assertThat(result).isPresent();
	}
}

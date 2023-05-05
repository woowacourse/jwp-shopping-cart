package cart.service.user;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.user.User;
import cart.service.user.dto.UserDto;

@Transactional
@SpringBootTest
@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
		rs.getLong("id"),
		rs.getString("email"),
		rs.getString("password")
	);

	@Test
	void findAllTest() {
		//given
		final List<User> users = jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);

		//when
		final List<UserDto> userDtos = userService.findAll();

		//then
		Assertions.assertThat(userDtos).hasSize(users.size());
	}
}

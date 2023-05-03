package cart.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.user.UserDao;
import cart.domain.user.User;
import cart.service.user.dto.UserDto;

@Service
@Transactional
public class UserService {

	private final UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional(readOnly = true)
	public List<UserDto> findAll() {
		return userDao.findAll().stream()
			.map(this::mapUserToUserDto)
			.collect(Collectors.toList());
	}

	private UserDto mapUserToUserDto(final User user) {
		return new UserDto(user.getId(), user.getEmail(), user.getPassword());
	}

}

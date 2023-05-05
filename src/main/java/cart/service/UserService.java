package cart.service;

import cart.dao.JdbcUserTableDao;
import cart.entity.User;
import cart.service.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final JdbcUserTableDao jdbcUserTableDao;

    public UserService(JdbcUserTableDao jdbcUserTableDao) {
        this.jdbcUserTableDao = jdbcUserTableDao;
    }

    public List<UserDto> getAllUser(){
        return jdbcUserTableDao.readAll()
                .stream()
                .map(user ->
                        new UserDto(
                                user.getEmail(),
                                user.getPassword()
                        ))
                .collect(Collectors.toList());
    }
    public boolean authUser(final UserDto userDto){
        User user = jdbcUserTableDao.findByEmail(userDto.getEmail());
        return user.authorization(userDto.getEmail(),userDto.getPassword());
    }
}

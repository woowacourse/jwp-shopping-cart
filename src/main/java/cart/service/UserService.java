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
                        new UserDto.Builder()
                                .id(user.getId())
                                .password(user.getPassword())
                                .email(user.getEmail())
                                .build())
                .collect(Collectors.toList());
    }
    public boolean authUser(final UserDto userDto){
        boolean check= jdbcUserTableDao.findByEmail(userDto.getEmail()).authorization(userDto.getEmail(),userDto.getPassword());
        return check;
    }

    public Long findLoginUserId(final String email){
        return jdbcUserTableDao.findByEmail(email).getId();
    }
}

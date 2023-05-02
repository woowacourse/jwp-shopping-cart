package cart.service;

import cart.dto.UserAuthenticationDto;
import cart.repository.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public int findByAuthenticationDto(final UserAuthenticationDto authenticationDto) {
        return userDao.findIdByEmailAndPassword(authenticationDto.getEmail(), authenticationDto.getPassword());
    }
}

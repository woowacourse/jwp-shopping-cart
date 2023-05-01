package cart.service;

import cart.dao.UserDao;
import cart.dao.entity.UserEntity;
import cart.dto.ResponseUserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public List<ResponseUserDto> findAll() {
        final List<UserEntity> userEntities = userDao.findAll();
        return userEntities.stream()
                .map(ResponseUserDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long findIdByEmail(final String email) {
        final UserEntity userEntity = userDao.findByEmail(email);
        return userEntity.getId();
    }
}

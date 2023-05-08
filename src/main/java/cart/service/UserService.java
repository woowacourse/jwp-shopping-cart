package cart.service;

import cart.dto.request.SignUpRequestDto;
import cart.dto.response.UserResponseDto;
import cart.entity.UserEntity;
import cart.repository.UserDao;
import cart.service.converter.UserConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserResponseDto> findAll() {
        final List<UserEntity> entities = userDao.findAll();
        return UserConverter.entitiesToResponseDtos(entities);
    }

    public int signUp(final SignUpRequestDto requestDto) {
        final UserEntity userEntity = UserConverter.requestDtoToEntity(requestDto);
        return userDao.create(userEntity);
    }
}

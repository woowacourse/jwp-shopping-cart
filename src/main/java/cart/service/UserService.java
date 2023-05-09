package cart.service;

import cart.dto.request.LoginRequestDto;
import cart.dto.request.SignUpRequestDto;
import cart.dto.response.UserResponseDto;
import cart.entity.UserEntity;
import cart.exception.DuplicateEmailException;
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
        checkDuplicateEmail(requestDto.getEmail());
        final UserEntity userEntity = UserConverter.requestDtoToEntity(requestDto);
        return userDao.create(userEntity);
    }

    private void checkDuplicateEmail(final String email) {
        if (userDao.findIdByEmail(email) != null) {
            throw new DuplicateEmailException();
        }
    }

    public UserResponseDto login(final LoginRequestDto loginRequestDto) {
        final UserEntity user =
                userDao.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return UserConverter.entityToResponseDto(user);
    }
}

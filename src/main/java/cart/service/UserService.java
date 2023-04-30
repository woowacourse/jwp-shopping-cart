package cart.service;

import cart.controller.dto.UserDto;
import cart.domain.User;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.UserDao;
import cart.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public long save(final UserDto userDto) {
        final UserEntity userEntity = convertToEntity(userDto);
        return userDao.insert(userEntity);
    }

    public UserDto getById(final Long id) {
        return userDao.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
    }

    private UserEntity convertToEntity(final UserDto userDto) {
        final User user = User.create(userDto.getEmail(), userDto.getPassword(),
                userDto.getNickname(), userDto.getTelephone());
        final String encodedPassword = encodePassword(user.getPassword());
        return new UserEntity(user.getEmail(), encodedPassword, user.getNickname(), user.getTelephone());
    }

    private UserDto convertToDto(final UserEntity userEntity) {
        final String decodedPassword = decodePassword(userEntity.getPassword());
        return new UserDto(userEntity.getId(), userEntity.getEmail(), decodedPassword,
                userEntity.getNickname(), userEntity.getTelephone());
    }

    private String encodePassword(final String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private String decodePassword(final String encodedPassword) {
        return new String(Base64.getDecoder().decode(encodedPassword.getBytes()), StandardCharsets.UTF_8);
    }
}

package cart.service;

import cart.controller.dto.auth.AuthInfoDto;
import cart.controller.dto.request.UserRequest;
import cart.controller.dto.response.UserResponse;
import cart.dao.UserDao;
import cart.domain.Email;
import cart.domain.Password;
import cart.domain.User;
import cart.exception.NotFoundResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public Long saveUser(final UserRequest userRequest) {
        User user = new User.Builder()
                .email(new Email(userRequest.getEmail()))
                .password(new Password(userRequest.getPassword()))
                .build();
        return userDao.save(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> loadAllUser() {
        List<User> users = userDao.findAll();
        return users.stream()
                    .map(UserResponse::from)
                    .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse loadUser(final Long userId) {
        Optional<User> findUser = userDao.findById(userId);
        User user = findUser.orElseThrow(() -> new NotFoundResultException("존재하지 않는 사용자입니다."));
        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public boolean isExistUser(final AuthInfoDto authInfoDto) {
        User user = new User.Builder()
                .email(new Email(authInfoDto.getEmail()))
                .password(new Password(authInfoDto.getEmail()))
                .build();
        userDao.findByEmailAndPassword(user)
               .orElseThrow(() -> new NotFoundResultException("존재하지 않는 사용자입니다."));
        return true;
    }

    public void updateUser(final Long userId, final UserRequest userRequest) {
        validateExistUser(userId);
        User user = new User.Builder()
                .id(userId)
                .email(new Email(userRequest.getEmail()))
                .password(new Password(userRequest.getPassword()))
                .build();
        userDao.update(user);
    }

    public void deleteUser(final Long userId) {
        validateExistUser(userId);
        userDao.deleteBy(userId);
    }

    private void validateExistUser(Long userId) {
        Optional<User> findUser = userDao.findById(userId);
        if (findUser.isEmpty()) {
            throw new NotFoundResultException("존재하지 않는 사용자입니다.");
        }
    }
}

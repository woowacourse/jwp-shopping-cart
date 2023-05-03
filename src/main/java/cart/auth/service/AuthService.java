package cart.auth.service;

import cart.auth.repository.AuthDao;
import cart.auth.dto.AuthenticationDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthDao authDao;

    public AuthService(final AuthDao authDao) {
        this.authDao = authDao;
    }

    public Integer getUserId(final AuthenticationDto authenticationDto) {
        return authDao.findIdByEmailAndPassword(authenticationDto.getEmail(), authenticationDto.getPassword());
    }
}

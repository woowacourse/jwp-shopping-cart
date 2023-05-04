package cart.domain.user.service;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import cart.domain.user.service.dto.CartUserDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CartUserService {
    private final CartUserRepository cartUserRepository;

    public CartUserService(final CartUserRepository cartUserRepository) {
        this.cartUserRepository = cartUserRepository;
    }

    public List<CartUserDto> getAllCartUsers() {
        final List<CartUser> allUsers = cartUserRepository.findAll();

        return allUsers.stream()
                .map(CartUserDto::from)
                .collect(Collectors.toList());
    }

}

package cart.domain.user.service;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import cart.domain.user.service.dto.CartUserDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartUserService {
    private final CartUserRepository cartUserRepository;

    public CartUserService(CartUserRepository cartUserRepository) {
        this.cartUserRepository = cartUserRepository;
    }

    public List<CartUserDto> getAllCartUsers() {
        List<CartUser> allUsers = cartUserRepository.findAll();

        return allUsers.stream()
                .map(CartUserDto::from)
                .collect(Collectors.toList());
    }

}

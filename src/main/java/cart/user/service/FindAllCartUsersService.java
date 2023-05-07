package cart.user.service;

import cart.user.domain.CartUser;
import cart.user.domain.CartUserRepository;
import cart.user.service.dto.CartUserResponseDto;
import cart.user.usecase.FindAllCartUsersUseCase;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class FindAllCartUsersService implements FindAllCartUsersUseCase {
    private final CartUserRepository cartUserRepository;

    public FindAllCartUsersService(final CartUserRepository cartUserRepository) {
        this.cartUserRepository = cartUserRepository;
    }

    public List<CartUserResponseDto> getAllCartUsers() {
        final List<CartUser> allUsers = cartUserRepository.findAll();

        return allUsers.stream()
                .map(CartUserResponseDto::from)
                .collect(Collectors.toList());
    }
}

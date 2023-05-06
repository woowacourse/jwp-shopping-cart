package cart.domain.user.usecase;

import cart.domain.user.service.dto.CartUserResponseDto;
import java.util.List;

public interface FindAllCartUsersUseCase {
    List<CartUserResponseDto> getAllCartUsers();
}

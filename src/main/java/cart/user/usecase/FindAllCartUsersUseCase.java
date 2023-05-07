package cart.user.usecase;

import cart.user.service.dto.CartUserResponseDto;
import java.util.List;

public interface FindAllCartUsersUseCase {
    List<CartUserResponseDto> getAllCartUsers();
}

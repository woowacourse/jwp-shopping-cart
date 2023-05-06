package cart.cart.dto;

import cart.auth.dto.UserResponseDTO;
import cart.catalog.dto.ResponseProductDto;

public class CartRequestDTO {
    
    private final long userId;
    private final long productId;
    
    public CartRequestDTO(final long userId, final long productId) {
        this.userId = userId;
        this.productId = productId;
    }
    
    public static CartRequestDTO of(final long userId, final long productId) {
        return new CartRequestDTO(userId, productId);
    }
    
    public static CartRequestDTO from(final UserResponseDTO userResponseDTO, final ResponseProductDto responseProductDto) {
        return new CartRequestDTO(userResponseDTO.getId(), responseProductDto.getId());
    }
    
    public long getUserId() {
        return this.userId;
    }
    
    public long getProductId() {
        return this.productId;
    }
}

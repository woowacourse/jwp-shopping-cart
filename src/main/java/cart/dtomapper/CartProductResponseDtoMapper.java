package cart.dtomapper;

import cart.dto.response.CartProductResponseDto;
import cart.entity.product.ProductEntity;
import java.util.List;

public class CartProductResponseDtoMapper {

    private CartProductResponseDtoMapper() {

    }

    public static CartProductResponseDto mapToDto(final ProductEntity productEntity) {
        return CartProductResponseDto.of(productEntity);
    }

    public static List<CartProductResponseDto> mapToDto(final List<ProductEntity> productEntities) {
        return CartProductResponseDto.asList(productEntities);
    }
}

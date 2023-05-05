package cart.dtomapper;

import cart.dto.response.CartProductResponseDto;
import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;

public class CartProductResponseDtoMapper {

    private CartProductResponseDtoMapper() {

    }

    public static CartProductResponseDto mapToDto(final ProductEntity productEntity) {
        return CartProductResponseDto.of(productEntity);
    }

    public static List<CartProductResponseDto> asList(final List<ProductEntity> productEntities) {
        return productEntities.stream()
            .map(CartProductResponseDtoMapper::mapToDto)
            .collect(Collectors.toList());
    }
}

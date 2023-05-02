package cart.dtomapper;

import cart.dto.ProductCategoryDto;
import cart.dto.response.ProductResponseDto;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseDtoMapper {

    private ProductResponseDtoMapper() {

    }

    public static ProductResponseDto mapTo(final ProductCategoryDto productCategoryDto) {
        return ProductResponseDto.of(productCategoryDto);
    }

    public static List<ProductResponseDto> mapTo(final List<ProductCategoryDto> productCategoryDtos) {
        return productCategoryDtos.stream()
            .map(ProductResponseDtoMapper::mapTo)
            .collect(Collectors.toList());
    }
}

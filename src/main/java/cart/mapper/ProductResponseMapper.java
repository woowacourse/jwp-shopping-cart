package cart.mapper;

import cart.dto.ProductDto;
import cart.dto.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseMapper {

    private ProductResponseMapper() {
    }

    public static ProductResponse from(final ProductDto productDto) {
        return ProductResponse.of(productDto.getId(), productDto.getName(), productDto.getImage(), productDto.getPrice());
    }

    public static List<ProductResponse> from(final List<ProductDto> productDtos) {
        return productDtos.stream()
                .map(ProductResponseMapper::from)
                .collect(Collectors.toList());
    }
}

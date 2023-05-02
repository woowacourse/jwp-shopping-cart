package cart.dto.response;

import cart.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final String image;
    private final Integer price;

    private ProductResponse(final Long id, final String name, final String image, final Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse from(final ProductDto productDto) {
        return new ProductResponse(productDto.getId(), productDto.getName(), productDto.getImage(), productDto.getPrice());
    }

    public static List<ProductResponse> from(final List<ProductDto> productDtos) {
        return productDtos.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static ProductResponse of(final Long id, final String name, final String image, final Integer price) {
        return new ProductResponse(id, name, image, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}

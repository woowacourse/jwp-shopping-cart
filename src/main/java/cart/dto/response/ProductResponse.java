package cart.dto.response;

import cart.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    private final long id;
    private final String name;
    private final String image;
    private final int price;

    private ProductResponse(final long id, final String name, final String image, final int price) {
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

    public static ProductResponse of(final long id, final String name, final String image, final int price) {
        return new ProductResponse(id, name, image, price);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}

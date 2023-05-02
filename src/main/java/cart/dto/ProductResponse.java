package cart.dto;

import cart.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final String image;

    private final Long price;

    public ProductResponse(final Long id, final String name, final String image, final Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static List<ProductResponse> mapProducts(List<ProductDto> products) {
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getImage(),
                        product.getPrice())
                ).collect(Collectors.toList());
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

    public Long getPrice() {
        return price;
    }
}

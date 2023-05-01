package cart.dto;

import cart.request.ProductRequest;
import java.util.Objects;

public class ProductDto {
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductDto(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductDto(final ProductRequest productRequest) {
        this.name = productRequest.getName();
        this.price = productRequest.getPrice();
        this.imageUrl = productRequest.getImageUrl();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductDto that = (ProductDto) o;
        return price == that.price && Objects.equals(name, that.name) && Objects.equals(imageUrl,
                that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, imageUrl);
    }
}

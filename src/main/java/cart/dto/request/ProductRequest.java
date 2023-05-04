package cart.dto.request;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.URL;

public class ProductRequest {

    @NotBlank
    private final String name;
    @PositiveOrZero
    private final int price;
    @NotBlank
    @URL
    private final String imageUrl;

    public ProductRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
        final ProductRequest that = (ProductRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price)
                && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, imageUrl);
    }
}

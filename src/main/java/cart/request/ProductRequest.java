package cart.request;

import cart.domain.ImageUrl;
import cart.domain.Name;
import cart.domain.Price;
import java.util.Objects;

public class ProductRequest {

    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;

    public ProductRequest(final String name, final int price, final String imageUrl) {
        this.name = new Name(name);
        this.price = new Price(price);
        this.imageUrl = new ImageUrl(imageUrl);
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
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

package cart.request;

import cart.domain.ImageUrl;
import cart.domain.Name;
import cart.domain.Price;

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
}

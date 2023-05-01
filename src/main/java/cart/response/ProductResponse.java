package cart.response;

import cart.domain.Id;
import cart.domain.ImageUrl;
import cart.domain.Name;
import cart.domain.Price;
import cart.domain.Product;

public class ProductResponse {

    private final Id id;
    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;

    public ProductResponse(final Product product) {
        this.id = new Id(product.getId());
        this.name = new Name(product.getName());
        this.price = new Price(product.getPrice());
        this.imageUrl = new ImageUrl(product.getImageUrl());
    }

    public long getId() {
        return id.getValue();
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

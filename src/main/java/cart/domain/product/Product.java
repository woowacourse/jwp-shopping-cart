package cart.domain.product;

import javax.validation.Valid;

public class Product {

    @Valid
    private final ProductName name;
    @Valid
    private final ProductPrice price;
    @Valid
    private final ProductImageUrl imageUrl;

    public Product(final String name, final int price, final String imageUrl) {
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
        this.imageUrl = new ProductImageUrl(imageUrl);
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

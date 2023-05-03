package cart.domain.product;

import javax.validation.Valid;

public class ProductEntity {

    private final ProductId id;
    @Valid
    private final Product product;

    public ProductEntity(final long id, final String name, final int price, final String imageUrl) {
        this.id = new ProductId(id);
        this.product = new Product(name, price, imageUrl);
    }

    public ProductEntity(final long id, final Product product) {
        this.id = new ProductId(id);
        this.product = product;
    }

    public long getId() {
        return id.getValue();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }
}

package cart.domain.product;

public class ProductEntity {

    private final ProductId id;
    private final Product product;

    public ProductEntity(final long id, final String name, final int price, final String imageUrl) {
        this.id = new ProductId(id);
        this.product = new Product(name, price, imageUrl);
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

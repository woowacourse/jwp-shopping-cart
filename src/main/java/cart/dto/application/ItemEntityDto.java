package cart.dto.application;

public class ItemEntityDto {

    private final long id;
    private final ProductDto product;

    public ItemEntityDto(final long id, final ProductEntityDto product) {
        this.id = id;
        this.product = product.getProduct();
    }

    public long getId() {
        return id;
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

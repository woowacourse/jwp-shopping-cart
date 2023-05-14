package cart.domain;

public class CartItem {

    private final Product product;
    private final Integer amount;

    public CartItem(Product product) {
        this(product, 1);
    }

    public CartItem(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public Long getProductId() {
        return product.getId();
    }

    public Integer getAmount() {
        return amount;
    }

}

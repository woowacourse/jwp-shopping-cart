package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Long customerId;
    private Product product;
    private Quantity quantity;

    public CartItem(Long id, Long customerId, Product product, int quantity) {
        this.id = id;
        this.customerId = customerId;
        this.product = product;
        this.quantity = new Quantity(quantity);
    }

    public CartItem(Long customerId, Product product, int quantity) {
        this(null, customerId, product, quantity);
    }

    public boolean isSameId(Long deleteProductId) {
        return product.getId().equals(deleteProductId);
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }
}

package woowacourse.shoppingcart.domain;

public class CartItem {
    private Long id;
    private Customer customer;
    private Product product;
    private int quantity;

    public CartItem(Long id, Customer customer, Product product, int quantity) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public Long getProductId() {
        return product.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean matchProductId(Long id) {
        return this.product.matchId(id);
    }
}

package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Integer quantity;
    private Product product;

    public CartItem(final long id, final int quantity, final Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return product.getId();
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

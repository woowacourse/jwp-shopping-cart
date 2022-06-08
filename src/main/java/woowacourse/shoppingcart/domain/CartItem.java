package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Product product;
    private Long quantity;
    private boolean checked;

    private CartItem() {
    }

    public CartItem(Long id, Product product, Long quantity, boolean checked) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}

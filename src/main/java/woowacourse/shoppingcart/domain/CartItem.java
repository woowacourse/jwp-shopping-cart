package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Product product;
    private int quantity;
    private boolean checked;

    public CartItem(Long id, Product product, int quantity, boolean checked) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.checked = checked;
    }

    public CartItem(Product product, int quantity, boolean checked) {
        this(null, product, quantity, checked);
    }

    public CartItem(Long id, int quantity, boolean checked) {
        this(id, null, quantity, checked);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}

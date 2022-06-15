package woowacourse.shoppingcart.domain;

public class CartItem {

    private static final int MIN_QUANTITY = 1;

    private Long id;
    private int quantity;
    private Product product;

    public CartItem() {
    }

    public CartItem(final Long id, final Product product) {
        this(id, product, MIN_QUANTITY);
    }

    public CartItem(Product product, int quantity) {
        this(null, product, quantity);
    }

    public CartItem(final Long id, final Product product, final int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
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

    public int getQuantity() {
        return quantity;
    }
}

package woowacourse.shoppingcart.domain;

public class Cart {

    private Long cartItemId;
    private Product product;
    private int quantity;

    public Cart() {
    }

    public Cart(final Long id, final Product product, final int quantity) {
        this.cartItemId = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Cart(final Long id, final Long productId, final String name, final int price, final String imageUrl,
                final String description, final int stock) {
        this(id, new Product(productId, name, price, imageUrl, description, stock), 0);
    }

    public Long getCartItemId() {
        return cartItemId;
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

    public String getDescription() {
        return product.getDescription();
    }

    public int getStock() {
        return product.getStock();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}

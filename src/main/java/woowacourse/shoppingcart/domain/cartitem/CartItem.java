package woowacourse.shoppingcart.domain.cartitem;

import woowacourse.shoppingcart.domain.Product;

public class CartItem {

    private static final int DEFAULT_QUANTITY = 1;

    private long id;
    private long productId;
    private String name;
    private int price;
    private String imageUrl;
    private Quantity quantity;

    public CartItem(long id, long productId, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = new Quantity(quantity);
    }

    public CartItem() {
    }

    public CartItem(final long id, final Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), DEFAULT_QUANTITY);
    }

    public CartItem(long id, Product product, int quantity) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() { return quantity.value(); }

    public void updateQuantity(int quantity) {
        this.quantity.updateQuantity(quantity);
    }
}

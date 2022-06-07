package woowacourse.shoppingcart.domain;

public class Cart {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;
    private int quantity;

    public Cart(final Long id, final Product product, final int quantity) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
    }

    public Cart(final Long id, final Long productId, final String name,
                final int price, final String imageUrl, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public void updateQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
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

    public int getQuantity() {
        return quantity;
    }
}

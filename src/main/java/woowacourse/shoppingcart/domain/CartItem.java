package woowacourse.shoppingcart.domain;

public class CartItem {

    private static final int DEFAULT_QUANTITY = 1;

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    private CartItem() {
    }

    public CartItem(Product product) {
        this(null, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), DEFAULT_QUANTITY);
    }

    public CartItem(final Long id, final Long productId, final String name, final int price, final String imageUrl,
                    final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

package woowacourse.shoppingcart.domain;

public class Cart {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final  String imageUrl;
    private final int quantity;

    private Cart(final Long id, final Long productId, final String name, final int price, final String imageUrl, int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static Cart from(final CartItem cartItem, final Product product) {
        return new Cart(cartItem.getId(), product.getId(), product.getName(),
                product.getPrice(), product.getImageUrl(), cartItem.getQuantity());
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

package woowacourse.shoppingcart.domain;

public class Cart {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private Quantity quantity;

    public Cart() {
    }

    public Cart(final Long productId, final String name, final int price, final String imageUrl, final int quantity) {
        this(null, productId, name, price, imageUrl, quantity);
    }

    public Cart(final Long id, final Long productId, final String name, final int price, final String imageUrl, final int quantiy) {
        this(id, productId, name, price, imageUrl, new Quantity(quantiy));
    }

    public Cart(final Long id, final Long productId, final String name, final int price, final String imageUrl, final Quantity quantiy) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantiy;
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

    public Quantity getQuantity() {
        return quantity;
    }

    public boolean isSameId(final Long cartId) {
        return this.id.equals(cartId);
    }
}

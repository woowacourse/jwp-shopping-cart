package woowacourse.shoppingcart.domain;

public class Cart {

    private long id;
    private long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;
    private int totalPrice;

    public Cart() {
    }

    public Cart(final long id, final Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), 1);
    }

    public Cart(long id, long productId, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}

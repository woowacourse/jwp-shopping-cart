package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;
    private int count;

    public CartItem() {
    }

    public CartItem(Long id, int count, Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getQuantity(),
            count);
    }

    public CartItem(Long id, Long productId, String name, int price, String imageUrl,
        int quantity, int count) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.count = count;
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

    public int getCount() {
        return count;
    }
}

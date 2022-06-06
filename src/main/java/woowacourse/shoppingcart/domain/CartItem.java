package woowacourse.shoppingcart.domain;

public class CartItem {

    private static final int DEFAULT_QUANTITY = 1;

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    public CartItem(Long id, Long productId, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public CartItem(final Long id, final Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), DEFAULT_QUANTITY);
    }

    public CartItem(Long id, Product product, int quantity) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
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

    public void updateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("올바른 수량이 아닙니다.");
        }
        this.quantity = quantity;
    }
}

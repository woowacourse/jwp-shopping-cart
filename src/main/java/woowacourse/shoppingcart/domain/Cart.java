package woowacourse.shoppingcart.domain;

public class Cart {

    private static final int POSITIVE_DIGIT_STANDARD = 0;

    private final Long id;
    private final Long customerId;
    private final Long productId;
    private final String name;
    private final Integer price;
    private final String imageUrl;
    private final Integer quantity;

    public static Cart of(Long customerId, Long productId, Integer quantity) {
        return new Cart(null, customerId, productId, null, null, null, quantity);
    }

    public Cart(Long id, Long customerId, Product product, Integer quantity) {
        this(id, customerId, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
    }

    public Cart(Long id, Long customerId, Long productId, String name, Integer price, String imageUrl,
                Integer quantity) {
        validateQuantity(quantity);
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity <= POSITIVE_DIGIT_STANDARD) {
            throw new IllegalArgumentException("올바르지 않은 상품 수량 형식입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

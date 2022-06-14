package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer price;
    private String name;
    private String imageUrl;

    public OrderDetail() {
    }

    public OrderDetail(Long productId, int quantity) {
        this(null, productId, quantity);
    }

    public OrderDetail(Long id, Long productId, int quantity) {
        this(id, productId, null, null, null, quantity);
    }

    public OrderDetail(Long id, Long productId, Integer price, String name, String imageUrl, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static OrderDetail of(Product product, Integer quantity) {
        return new OrderDetail(null, product.getId(), product.getPrice(), product.getName(), product.getImageUrl(),
                quantity);
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

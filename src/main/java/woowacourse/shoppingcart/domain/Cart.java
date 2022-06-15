package woowacourse.shoppingcart.domain;

public class Cart {

    private Long id;
    private Long productId;
    private Integer quantity;
    private String name;
    private int price;
    private String imageUrl;

    public Cart() {
    }

    public Cart(final Long id, final Long productId, final Integer quantity, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Cart(Long id, Integer quantity, String name, int price, String imageUrl) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
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
}

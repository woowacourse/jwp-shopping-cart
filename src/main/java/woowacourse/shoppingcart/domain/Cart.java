package woowacourse.shoppingcart.domain;

public class Cart {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;

    private Cart(Long id, Long productId, String name, int price, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Cart(Long id, Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
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
}

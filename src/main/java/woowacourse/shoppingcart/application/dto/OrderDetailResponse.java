package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.product.Product;

public class OrderDetailResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    public OrderDetailResponse(Long id, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static OrderDetailResponse from(Product product, int quantity) {
        return new OrderDetailResponse(
                product.getId(),
                product.getName().getValue(),
                product.getPrice().getValue(),
                product.getImageUrl().getValue(),
                quantity
        );
    }

    public Long getId() {
        return id;
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

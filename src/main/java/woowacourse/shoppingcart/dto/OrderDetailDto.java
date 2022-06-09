package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailDto {
    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    public OrderDetailDto(OrderDetail orderDetail) {
        this(orderDetail.getProductId(), orderDetail.getQuantity(), orderDetail.getPrice(),
                orderDetail.getName(), orderDetail.getImageUrl());
    }

    public OrderDetailDto(final Long productId, final int quantity, final int price, final String name, final String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

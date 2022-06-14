package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

public class OrderDetailResponse {

    private Long productId;
    private String name;
    private Integer quantity;
    private Integer price;
    private String image;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(Long productId, String name, Integer quantity, Integer price, String image) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public static OrderDetailResponse from(OrderDetail orderDetail) {
        Product product = orderDetail.getProduct();
        return new OrderDetailResponse(product.getId(), product.getName(), orderDetail.getQuantity(),
                product.getPrice(), product.getImage());
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}

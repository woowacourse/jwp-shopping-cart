package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

public class OrderDetailResponse {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final OrderDetail orderDetail) {
        final Product product = orderDetail.getProduct();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.name = product.getName();
        this.imageUrl = product.getImageUrl();
        this.quantity = orderDetail.getQuantity();
    }

    public Long getProductId() {
        return productId;
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

    public int getQuantity() {
        return quantity;
    }
}

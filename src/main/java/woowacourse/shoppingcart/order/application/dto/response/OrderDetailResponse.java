package woowacourse.shoppingcart.order.application.dto.response;

import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.product.domain.Product;

public class OrderDetailResponse {

    private Long productId;
    private String name;
    private long price;
    private String image;
    private long quantity;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(Long productId, String name, long price, String image, long quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public static OrderDetailResponse of(final Product product, final OrderDetail orderDetail) {
        return new OrderDetailResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(),
                orderDetail.getQuantity());
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public long getQuantity() {
        return quantity;
    }
}

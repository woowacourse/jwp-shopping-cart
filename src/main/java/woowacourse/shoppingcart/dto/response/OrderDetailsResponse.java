package woowacourse.shoppingcart.dto.response;

import java.util.ArrayList;
import java.util.List;
import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailsResponse {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    public OrderDetailsResponse() {
    }

    public OrderDetailsResponse(Long productId, int quantity, int price, String name,
        String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static List<OrderDetailsResponse> of(List<OrderDetail> orderDetails) {
        List<OrderDetailsResponse> ordersDetails = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            ordersDetails.add(new OrderDetailsResponse(
                orderDetail.getProductId(),
                orderDetail.getQuantity(),
                orderDetail.getPrice(),
                orderDetail.getName(),
                orderDetail.getImageUrl()
            ));
        }
        return ordersDetails;
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

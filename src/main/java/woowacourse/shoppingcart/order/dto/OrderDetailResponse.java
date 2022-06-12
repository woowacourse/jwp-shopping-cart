package woowacourse.shoppingcart.order.dto;

import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.dto.ThumbnailImageDto;

public class OrderDetailResponse {
    private Long productId;
    private Integer quantity;
    private Integer price;
    private String name;
    private ThumbnailImageDto thumbnailImage;

    public OrderDetailResponse(Long productId, Integer quantity, Integer price, String name,
                               ThumbnailImageDto thumbnailImage) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.thumbnailImage = thumbnailImage;
    }

    public static OrderDetailResponse of(OrderDetail orderDetail) {
        final Product product = orderDetail.getProduct();
        return new OrderDetailResponse(product.getId(), orderDetail.getQuantity(), product.getPrice(),
                product.getName(),
                ThumbnailImageDto.from(product.getThumbnailImage())
        );
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ThumbnailImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}

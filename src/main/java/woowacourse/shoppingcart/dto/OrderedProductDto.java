package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;

public class OrderedProductDto {

    private final long productId;
    private final int price;
    private final String name;
    private final int quantity;
    private final ThumbnailImageDto thumbnailImage;

    public OrderedProductDto(long productId, int price, String name, int quantity,
        ThumbnailImageDto thumbnailImage) {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.thumbnailImage = thumbnailImage;
    }

    public static OrderedProductDto from(OrderDetail orderDetail) {
        Product product = orderDetail.getProduct();
        return new OrderedProductDto(product.getId(), product.getPrice(), product.getName(),
            orderDetail.getQuantity(),
            new ThumbnailImageDto(product.getThumbnailImageUrl(), product.getThumbnailImageAlt()));
    }

    public long getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public ThumbnailImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}

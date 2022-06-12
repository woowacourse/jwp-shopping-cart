package woowacourse.shoppingcart.cartitem.dto;

import woowacourse.shoppingcart.product.dto.ThumbnailImageDto;
import woowacourse.shoppingcart.product.domain.Product;

public class CartItemResponse {

    private Long id;
    private Long productId;
    private Integer price;
    private String name;
    private Integer quantity;
    private ThumbnailImageDto thumbnailImage;

    public CartItemResponse(Long id, Long productId, Integer price, String name, Integer quantity,
                            ThumbnailImageDto thumbnailImageDto) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.thumbnailImage = thumbnailImageDto;
    }

    public static CartItemResponse of(Long id, Integer quantity, Product product) {
        return new CartItemResponse(id, product.getId(), product.getPrice(), product.getName(),
                quantity, ThumbnailImageDto.from(product.getThumbnailImage()));
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ThumbnailImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}

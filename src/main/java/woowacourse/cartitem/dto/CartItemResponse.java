package woowacourse.cartitem.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import woowacourse.cartitem.domain.CartItem;

@JsonTypeName("cartItem")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class CartItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageURL;
    private int quantity;

    private CartItemResponse() {
    }

    public CartItemResponse(
        final Long id, final Long productId, final String name,
        final int price, final String imageURL, final int quantity
    ) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(
            cartItem.getId(), cartItem.getProductId(),
            cartItem.getName(), cartItem.getPrice().getValue(),
            cartItem.getImageURL(), cartItem.getQuantity().getValue()
        );
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

    public int getQuantity() {
        return quantity;
    }

    public String getImageURL() {
        return imageURL;
    }
}

package woowacourse.shoppingcart.dto.cartItem;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import woowacourse.shoppingcart.domain.CartItem;

@JsonTypeName("cartItem")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public class CartItemResponse {

    private long id;
    private long productId;
    private String name;
    private int price;
    private int quantity;
    private String imageURL;

    private CartItemResponse() {
    }

    public CartItemResponse(long id, long productId, String name, int price, int quantity, String imageURL) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(cartItem.getId(), cartItem.getProduct().getId(), cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(), cartItem.getQuantity(), cartItem.getProduct().getImageUrl());
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
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

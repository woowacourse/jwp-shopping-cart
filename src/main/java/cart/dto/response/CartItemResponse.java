package cart.dto.response;

import cart.domain.CartItem;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class CartItemResponse {

    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final String imageUrl;

    public CartItemResponse(Long id, String name, BigDecimal price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CartItemResponse(CartItem cartItem) {
        this(cartItem.getCartItemId(), cartItem.getProduct().getName(), cartItem.getProduct().getPrice(), cartItem.getProduct().getImageUrl());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

package cart.dto;

import cart.entity.Cart;
import cart.vo.Email;

public class CartDto {

    private final Email email;
    private final Long productId;

    public CartDto(Email email, Long productId) {
        this.email = email;
        this.productId = productId;
    }

    public Email getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }

    public Cart toEntity() {
        return new Cart.Builder()
                .email(email)
                .productId(productId)
                .build();
    }

}

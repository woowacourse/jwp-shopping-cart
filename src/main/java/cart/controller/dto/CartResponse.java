package cart.controller.dto;

import java.util.List;

public class CartResponse {

    private final List<CartDto> cartDtos;

    public CartResponse(List<CartDto> cartDtos) {
        this.cartDtos = cartDtos;
    }

    public List<CartDto> getCartDtos() {
        return cartDtos;
    }
}

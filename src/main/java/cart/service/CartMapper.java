package cart.service;

import cart.dao.entity.Cart;
import cart.dto.CartSaveRequest;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    CartMapper() {
    }

    public Cart mapFrom(final CartSaveRequest saveRequest) {
        return new Cart(saveRequest.getUserId(), saveRequest.getProductId(), saveRequest.getCount());
    }
}

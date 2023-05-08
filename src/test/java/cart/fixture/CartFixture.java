package cart.fixture;

import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;

public class CartFixture {
    public static class CART {
        public static CartRequest REQUEST = new CartRequest(1L, 1);
        public static CartResponse RESPONSE = new CartResponse(1L, ProductFixture.SNACK.RESPONSE, 1);
    }

    public static class CART2 {
        public static CartRequest REQUEST = new CartRequest(1L, 1);
        public static CartResponse RESPONSE = new CartResponse(1L, ProductFixture.SNACK.RESPONSE, 1);
    }

    public static class CART3 {
        public static CartRequest REQUEST = new CartRequest(1L, 1);
        public static CartResponse RESPONSE = new CartResponse(1L, ProductFixture.SNACK.RESPONSE, 3);
    }
}

package woowacourse.shoppingcart.acceptance;

import woowacourse.shoppingcart.dto.CartItemAddRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityUpdateRequest;

public class ShoppingCartAcceptanceTestFixture {

    static final String PRODUCTS_FIND_URI = "/api/products";
    static final String CART_URI_EXCEPT_GET = "/api/carts/products";
    static final String CART_GET_URI = "/api/carts";
    static final CartItemAddRequest VALID_CART_ITEM_ADD_REQUEST1 = new CartItemAddRequest(1L, 5);
    static final CartItemAddRequest VALID_CART_ITEM_ADD_REQUEST2 = new CartItemAddRequest(2L, 8);
    static final CartItemAddRequest INVALID_PRODUCT_ID_CART_ITEM_ADD_REQUEST = new CartItemAddRequest(20L, 5);
    static final CartItemQuantityUpdateRequest VALID_CART_ITEM_QUANTITY_UPDATE_REQUEST = new CartItemQuantityUpdateRequest(
            1L, 8);
}

package cart.presentation.adapter;

import cart.business.domain.cart.CartItem;
import cart.business.domain.cart.CartItemId;
import cart.business.domain.member.MemberId;
import cart.business.domain.product.Product;
import cart.business.domain.product.ProductId;
import cart.presentation.dto.CartItemDto;

public class CartItemConverter {

    private static final Integer NULL_ID = null;

    public static CartItem toEntity(Integer productId, Integer memberId) {
        return new CartItem(new CartItemId(NULL_ID), new ProductId(productId), new MemberId(memberId));
    }

    public static CartItemDto toDto(Product product, Integer cartItemId) {
        return new CartItemDto(cartItemId, product.getName(),
                product.getUrl(), product.getPrice());
    }
}

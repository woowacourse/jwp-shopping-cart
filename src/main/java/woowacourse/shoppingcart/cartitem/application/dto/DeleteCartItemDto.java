package woowacourse.shoppingcart.cartitem.application.dto;

import java.util.List;
import woowacourse.shoppingcart.cartitem.ui.dto.DeleteCartItemRequest;

public class DeleteCartItemDto {
    private final List<Long> cartItemIds;
    private final String email;

    public DeleteCartItemDto(List<Long> cartItemIds, String email) {
        this.cartItemIds = cartItemIds;
        this.email = email;
    }

    public static DeleteCartItemDto from(DeleteCartItemRequest deleteCartItemRequest, String email) {
        return new DeleteCartItemDto(deleteCartItemRequest.getCartItemIds(), email);
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }

    public String getEmail() {
        return email;
    }
}

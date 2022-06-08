package woowacourse.shoppingcart.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidCartItemException;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Builder
public class CartItem {

    @Include
    private Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    public CartItem(Product product, int quantity) {
        this(null, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
        validateQuantity(quantity);
    }

    public CartItem(Long id, Product product, int quantity) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
        validateQuantity(quantity);
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidCartItemException(ErrorCode.QUANTITY_FORMAT, "수량 형식이 맞지 않습니다.");
        }
    }

    public CartItem createWithId(Long id) {
        return new CartItem(id, productId, name, price, imageUrl, quantity);
    }

    public boolean isSameProductId(Long productId) {
        return this.productId.equals(productId);
    }
}

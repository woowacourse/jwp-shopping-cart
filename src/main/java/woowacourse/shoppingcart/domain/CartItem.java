package woowacourse.shoppingcart.domain;

import woowacourse.common.exception.CartItemException;
import woowacourse.common.exception.OrderException;
import woowacourse.common.exception.dto.ErrorResponse;

public class CartItem {
    private final Long id;
    private final int quantity;
    private final Product product;

    public CartItem(Long id, int quantity, Product product) {
        validate(quantity);
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    private void validate(int quantity) {
        if (quantity <= 0) {
            throw new CartItemException("구매 수량이 적절하지 않습니다.", ErrorResponse.INVALID_QUANTITY);
        }
    }

    public void order() {
        checkQuantity();
        product.order(quantity);
    }

    private void checkQuantity() {
        if (quantity > product.getStockQuantity()) {
            throw new OrderException("재고보다 많은 양을 구매할 수 없습니다.", ErrorResponse.OUT_OF_STOCK);
        }
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}

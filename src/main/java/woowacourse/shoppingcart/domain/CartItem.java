package woowacourse.shoppingcart.domain;

import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.OutOfStockException;

public class CartItem {

    private Long id;
    private Product product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public void validateOutOfStock() {
        int stock = product.getStockQuantity();
        if (quantity > stock) {
            throw new OutOfStockException("주문한 수량이 재고보다 더 많습니다.", ErrorResponse.OUT_OF_STOCK);
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

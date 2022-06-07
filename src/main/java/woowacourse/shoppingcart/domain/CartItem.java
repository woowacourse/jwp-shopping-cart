package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class CartItem {

    private final Long id;
    private final Long memberId;
    private final Long productId;
    private Integer quantity;

    public CartItem(Long memberId, Long productId, Integer quantity) {
        this(null, memberId, productId, quantity);
    }

    public CartItem(Long id, Long memberId, Long productId, Integer quantity) {
        validateQuantity(quantity);
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("상품 수량은 0보다 커야 합니다.");
        }
    }

    public void addOne() {
        this.quantity++;
    }

    public void add(int quantity) {
        validateQuantity(quantity);
        this.quantity += quantity;
    }

    public void reduceOne() {
        if (quantity == 0) {
            throw new IllegalArgumentException("더 이상 수량을 줄일 수 없습니다.");
        }
        this.quantity--;
    }

    public boolean isEmpty() {
        return this.quantity == 0;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && Objects.equals(id, cartItem.id) && Objects.equals(
                memberId, cartItem.memberId) && Objects.equals(productId, cartItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, productId, quantity);
    }
}

package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class Cart {

    private Long id;
    private Long memberId;
    private Product product;
    private int quantity;

    public Cart(final Long memberId, final Product product, final int quantity) {
        this(null, memberId, product, quantity);
    }

    public Cart(final Long id, final Long memberId, final Product product, final int quantity) {
        validateCartQuantity(quantity);
        this.id = id;
        this.memberId = memberId;
        this.product = product;
        this.quantity = quantity;
    }

    private void validateCartQuantity(final int quantity) {
        if (quantity < 1) {
            throw new InvalidCartItemException("[ERROR] 장바구니 물품 개수를 1개 이상 입력해주세요.");
        }
    }

    public void updateQuantity(final int quantity) {
        validateCartQuantity(quantity);
        this.quantity = quantity;
    }

    public int calculateTotalPrice() {
        return product.getPrice() * quantity;
    }

    public boolean isSameProduct(final Product product) {
        return this.product.equals(product);
    }

    public void addQuantity() {
        this.quantity++;
    }

    public boolean isNewlyAdded() {
        return quantity == 1;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Carts {

    private Long id;
    private Long memberId;
    private Product product;
    private int quantity;

    public Carts(final Long memberId, final Product product, final int quantity) {
        this(null, memberId, product, quantity);
    }

    public Carts(final Long id, final Long memberId, final Product product, final int quantity) {
        this.id = id;
        this.memberId = memberId;
        this.product = product;
        this.quantity = quantity;
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

    public int calculateTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Carts carts = (Carts) o;
        return Objects.equals(id, carts.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

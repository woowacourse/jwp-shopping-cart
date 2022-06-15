package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private Long id;
    private Cart cart;

    public OrderDetail(final Long id, final Cart cart) {
        this.id = id;
        this.cart = cart;
    }

    public long getTotalPrice() {
        return cart.getTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }
}

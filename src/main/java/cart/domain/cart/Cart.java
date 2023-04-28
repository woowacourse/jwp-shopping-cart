package cart.domain.cart;

import cart.domain.member.Member;
import cart.domain.product.Product;

public class Cart {

    private Long id;
    private final Member member;
    private final Product product;

    private Cart(final Long id, final Member member, final Product product) {
        this.id = id;
        this.member = member;
        this.product = product;
    }

    public static Cart from(final Long id, final Member member, final Product product) {
        return new Cart(id, member, product);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }
}

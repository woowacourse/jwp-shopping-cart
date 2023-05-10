package cart.cart.entity;

import cart.member.entity.Member;
import cart.product.entity.Product;

public class Cart {

    private final Long id;
    private final Product product;
    private final Member member;

    public Cart(Long id, Product product, Member member) {
        this.id = id;
        this.product = product;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }
}

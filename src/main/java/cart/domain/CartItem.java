package cart.domain;

import java.util.Objects;

public class CartItem {
    private Long id;
    private final Member member;
    private final Product product;

    public CartItem(Long id, Member member, Product product) {
        this.id = id;
        this.member = member;
        this.product = product;
    }

    public CartItem(Member member, Product product) {
        this.member = member;
        this.product = product;
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

    public void checkOwner(Member member) {
        if (!Objects.equals(this.member.getId(), member.getId())) {
            throw new RuntimeException();
        }
    }
}

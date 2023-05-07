package cart.domain.cart;

import cart.domain.member.Member;
import cart.domain.product.Product;

import java.util.Objects;

public class Item {

    private final Long id;
    private final Member member;
    private final Product product;

    public Item(Member member, Product product) {
        this(null, member, product);
    }

    public Item(Long id, Member member, Product product) {
        this.id = id;
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

    public long getMemberId() {
        return member.getId();
    }

    public long getProductId() {
        return product.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(member, item.getMember())
                && Objects.equals(product, item.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, product);
    }
}

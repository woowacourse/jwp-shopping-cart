package cart.domain.entity;

public class CartItem {

    private final Long id;
    private final Member member;
    private final Product product;

    private CartItem(final Long id, final Member member, final Product product) {
        this.id = id;
        this.member = member;
        this.product = product;
    }

    public static CartItem of(final Member member, final Product product) {
        return new CartItem(null, member, product);
    }

    public static CartItem of(final Long id, final Member member, final Product product) {
        return new CartItem(id, member, product);
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

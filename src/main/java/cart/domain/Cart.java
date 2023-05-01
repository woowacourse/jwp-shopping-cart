package cart.domain;

public class Cart {
    private final Product product;
    private final Member member;
    private int count;

    public Cart(Product product, Member member, int count) {
        this.product = product;
        this.member = member;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }

    public int getCount() {
        return count;
    }
}

package cart.entity;

public class PutCart {

    private final Member member;
    private final Item product;

    public PutCart(Member member, Item product) {
        this.member = member;
        this.product = product;
    }

    public Long getMemberId() {
        return member.getId();
    }

    public Long getProductId() {
        return product.getId();
    }
}

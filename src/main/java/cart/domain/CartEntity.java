package cart.domain;

public class CartEntity {

    private final Long id;
    private final MemberEntity member;
    private final ProductEntity product;

    public CartEntity(final Long id, final MemberEntity member, final ProductEntity product) {
        this.id = id;
        this.member = member;
        this.product = product;
    }

    public CartEntity(final MemberEntity member, final ProductEntity product) {
        this(null, member, product);
    }

    public Long getId() {
        return id;
    }

    public MemberEntity getMember() {
        return member;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public Long getMemberId() {
        return member.getId();
    }

    public Long getProductId() {
        return product.getId();
    }
}

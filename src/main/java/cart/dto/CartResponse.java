package cart.dto;

public class CartResponse {
    private final long id;
    private final MemberResponse member;
    private final ProductResponse product;

    public CartResponse(long id, MemberResponse member, ProductResponse product) {
        this.id = id;
        this.member = member;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public MemberResponse getMember() {
        return member;
    }

    public ProductResponse getProduct() {
        return product;
    }
}

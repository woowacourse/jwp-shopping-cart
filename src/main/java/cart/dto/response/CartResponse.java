package cart.dto.response;

public class CartResponse {
    private final long id;
    private final MemberResponse member;
    private final ProductResponse product;
    private final int count;

    public CartResponse(long id, MemberResponse member, ProductResponse product, int count) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.count = count;
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

    public int getCount() {
        return count;
    }
}

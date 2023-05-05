package cart.entity;

public class CartEntity {

    private final Integer id;
    private final Integer memberId;
    private final Integer productId;

    public CartEntity(final Integer id, final Integer memberId, final Integer productId) {
        validate(memberId, productId);
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public CartEntity(final Integer memberId, final Integer productId) {
        this(null, memberId, productId);
    }

    private void validate(final Integer memberId, final Integer productId) {
        if (memberId == null) {
            throw new IllegalArgumentException("DB 테이블의 memberId 은 not null 로 설정 되어 있습니다.");
        }
        if (productId == null) {
            throw new IllegalArgumentException("DB 테이블의 productId 은 not null 로 설정 되어 있습니다.");
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getProductId() {
        return productId;
    }

}

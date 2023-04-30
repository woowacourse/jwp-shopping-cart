package cart.dao.entity;

public class CartEntity {
    private final Long id;
    private final Long member_id;
    private final Long product_id;

    public CartEntity(Long id, Long member_id, Long product_id) {
        this.id = id;
        this.member_id = member_id;
        this.product_id = product_id;
    }

    public Long getId() {
        return id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public Long getProduct_id() {
        return product_id;
    }
}

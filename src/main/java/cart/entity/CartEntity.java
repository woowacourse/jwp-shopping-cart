package cart.entity;

import java.sql.Timestamp;

public class CartEntity {
    private final Long id;
    private final MemberEntity member;
    private final ProductEntity product;
    private int count;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public CartEntity(Long id, MemberEntity member, ProductEntity product, int count, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.count = count;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public int getCount() {
        return count;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void replace(int count) {
        this.count = count;
    }
}

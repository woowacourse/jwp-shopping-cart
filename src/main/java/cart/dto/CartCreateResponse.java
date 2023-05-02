package cart.dto;

import cart.domain.cart.entity.Cart;
import cart.domain.member.entity.Member;
import cart.domain.product.entity.Product;
import java.time.LocalDateTime;

public class CartCreateResponse {

    private final Long id;
    private final Product product;
    private final Member member;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CartCreateResponse(final Long id, final Product product, final Member member,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt) {
        this.id = id;
        this.product = product;
        this.member = member;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CartCreateResponse of(final Cart cart) {
        return new CartCreateResponse(
            cart.getId(),
            cart.getProduct(),
            cart.getMember(),
            cart.getCreatedAt(),
            cart.getUpdatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

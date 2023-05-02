package cart.domain.cart.entity;

import cart.domain.member.entity.Member;
import cart.domain.product.entity.Product;
import java.time.LocalDateTime;
import org.springframework.lang.Nullable;

public class Cart {

    private final Long id;
    private final Product product;
    private final Member member;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Cart(@Nullable final Long id, final Product product, final Member member,
        @Nullable final LocalDateTime createdAt,
        @Nullable final LocalDateTime updatedAt) {
        this.id = id;
        this.product = product;
        this.member = member;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

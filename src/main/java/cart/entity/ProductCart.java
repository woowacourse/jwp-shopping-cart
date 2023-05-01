package cart.entity;

import java.util.Objects;

public class ProductCart {

    private final Long id;
    private final Long productId;
    private final Long memberId;

    public ProductCart(Long productId, Long memberId) {
        this(null, productId, memberId);
    }

    public ProductCart(Long id, Long productId, Long memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCart that = (ProductCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

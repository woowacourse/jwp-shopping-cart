package cart.entity;

import java.util.Objects;

public class CartEntity {

    private final Integer id;
    private final Integer userId;
    private final Integer productId;

    public CartEntity(final Integer id, final Integer userId, final Integer productId) {
        validate(userId, productId);
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public CartEntity(final Integer userId, final Integer productId) {
        this(null, userId, productId);
    }

    private void validate(final Integer userId, final Integer productId) {
        if (Objects.isNull(userId) || Objects.isNull(productId)) {
            throw new IllegalArgumentException("Cart 의 userId, productId 는 null 을 허용하지 않습니다.");
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProductId() {
        return productId;
    }
}

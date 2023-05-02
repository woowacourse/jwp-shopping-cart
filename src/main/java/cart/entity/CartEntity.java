package cart.entity;

public class CartEntity {
  private final Long id;
  private final Long productId;
  private final Long memberId;
  private final int cartCount;

  public CartEntity(Long id, Long productId, Long memberId, int cartCount) {
    this.id = id;
    this.productId = productId;
    this.memberId = memberId;
    this.cartCount = cartCount;
  }

  public CartEntity(Long productId, Long memberId) {
    this(null, productId, memberId, 1);
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

  public int getCartCount() {
    return cartCount;
  }
}

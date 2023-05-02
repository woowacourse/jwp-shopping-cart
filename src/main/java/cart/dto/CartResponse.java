package cart.dto;

public class CartResponse {

  private final int cartCount;
  private final long productId;

  public CartResponse(int cartCount, long productId) {
    this.cartCount = cartCount;
    this.productId = productId;
  }

  public int getCartCount() {
    return cartCount;
  }

  public long getProductId() {
    return productId;
  }
}

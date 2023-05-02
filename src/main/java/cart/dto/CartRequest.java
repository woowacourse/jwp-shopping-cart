package cart.dto;

import javax.validation.constraints.NotNull;

public class CartRequest {

  @NotNull
  private final long productId;

  public CartRequest(long productId) {
    this.productId = productId;
  }

  public long getProductId() {
    return productId;
  }
}

package cart.dto;

import javax.validation.constraints.NotNull;

public class CartRequest {

  @NotNull
  private long productId;

  public CartRequest() {
  }

  public CartRequest(long productId) {
    this.productId = productId;
  }

  public long getProductId() {
    return productId;
  }
}

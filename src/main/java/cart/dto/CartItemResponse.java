package cart.dto;

import cart.entity.ProductEntity;

public class CartItemResponse {

  private final int cartCount;
  private final long productId;
  private final String name;
  private final String imageUrl;
  private final int price;

  public CartItemResponse(final int cartCount, final ProductEntity productEntity) {
    this.cartCount = cartCount;
    this.productId = productEntity.getId();
    this.name = productEntity.getName();
    this.imageUrl = productEntity.getImageUrl();
    this.price = productEntity.getPrice();
  }

  public int getCartCount() {
    return cartCount;
  }

  public long getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public int getPrice() {
    return price;
  }
}

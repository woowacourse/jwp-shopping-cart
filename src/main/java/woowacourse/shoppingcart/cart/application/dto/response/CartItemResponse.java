package woowacourse.shoppingcart.cart.application.dto.response;

import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.product.domain.Product;

public class CartItemResponse {

    private long productId;
    private String name;
    private long price;
    private String image;
    private long quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(long productId, String name, long price, String image, long quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public static CartItemResponse of(final Product product, final Cart cart) {
        return new CartItemResponse(
                product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), cart.getQuantity());
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public long getQuantity() {
        return quantity;
    }
}

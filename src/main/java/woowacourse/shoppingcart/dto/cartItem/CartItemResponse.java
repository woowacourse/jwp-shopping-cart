package woowacourse.shoppingcart.dto.cartItem;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class CartItemResponse {

    private Long productId;
    private String name;
    private Integer price;
    private String image;
    private Integer quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(Long productId, String name, Integer price, String image, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public static List<CartItemResponse> from(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static CartItemResponse from(CartItem cartItem) {
        Product product = cartItem.getProduct();
        return new CartItemResponse(cartItem.getId(), product.getName(), product.getPrice(), product.getImage(),
                cartItem.getQuantity());
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

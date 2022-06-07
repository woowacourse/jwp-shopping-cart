package woowacourse.cartitem.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.cartitem.domain.CartItem;

public class CartItemResponses {

    private List<CartItemDetailResponse> cartItems;

    private CartItemResponses() {
    }

    public CartItemResponses(final List<CartItemDetailResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartItemResponses from(final List<CartItem> cartItems) {
        final List<CartItemDetailResponse> values = cartItems.stream()
            .map(CartItemDetailResponse::from)
            .collect(Collectors.toList());

        return new CartItemResponses(values);
    }

    public List<CartItemDetailResponse> getCartItems() {
        return cartItems;
    }

    public static class CartItemDetailResponse {

        private Long id;
        private Long productId;
        private String name;
        private int price;
        private String imageURL;
        private int quantity;

        public CartItemDetailResponse() {
        }

        public CartItemDetailResponse(final Long id, final Long productId, final String name, final int price,
            final String imageURL, final int quantity) {
            this.id = id;
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.imageURL = imageURL;
            this.quantity = quantity;
        }

        public static CartItemDetailResponse from(final CartItem cartItem) {
            return new CartItemDetailResponse(
                cartItem.getId(), cartItem.getProductId(), cartItem.getName(),
                cartItem.getPrice().getValue(), cartItem.getImageURL(), cartItem.getQuantity().getValue()
            );
        }

        public Long getId() {
            return id;
        }

        public Long getProductId() {
            return productId;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String getImageURL() {
            return imageURL;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}

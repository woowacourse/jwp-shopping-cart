package woowacourse.cartitem.dto;

import woowacourse.cartitem.domain.CartItem;

public class CartItemResponse {

    private InnerCartItemResponse cartItem;

    private CartItemResponse() {
    }

    public CartItemResponse(final InnerCartItemResponse cartItem) {
        this.cartItem = cartItem;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(InnerCartItemResponse.from(cartItem));
    }

    public InnerCartItemResponse getCartItem() {
        return cartItem;
    }

    public static class InnerCartItemResponse {

        private Long id;
        private Long productId;
        private String name;
        private int price;
        private String imageURL;
        private int quantity;

        private InnerCartItemResponse() {
        }

        public InnerCartItemResponse(final Long id, final Long productId, final String name, final int price,
            final String imageURL, final int quantity) {
            this.id = id;
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.imageURL = imageURL;
            this.quantity = quantity;
        }

        public static InnerCartItemResponse from(CartItem cartItem) {
            return new InnerCartItemResponse(
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

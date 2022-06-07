package woowacourse.shoppingcart.dto.cartitem;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {

    private CartItemResponseNested cartItem;

    private CartItemResponse() {
    }

    public CartItemResponse(final CartItemResponseNested cartItem) {
        this.cartItem = cartItem;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(CartItemResponseNested.from(cartItem));
    }

    public CartItemResponseNested getCartItem() {
        return cartItem;
    }

    public static class CartItemResponseNested {

        private Long id;
        private Long productId;
        private String name;
        private int price;
        private int quantity;
        private String imageURL;

        private CartItemResponseNested() {
        }

        public CartItemResponseNested(final Long id, final Long productId, final String name, final int price,
                                      final int quantity, final String imageURL) {
            this.id = id;
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.imageURL = imageURL;
        }

        public static CartItemResponseNested from(final CartItem cartItem) {
            return new CartItemResponseNested(
                    cartItem.getId(),
                    cartItem.getProductId(),
                    cartItem.getName(),
                    cartItem.getPrice(),
                    cartItem.getQuantity(),
                    cartItem.getImageUrl()
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

        public int getQuantity() {
            return quantity;
        }

        public String getImageURL() {
            return imageURL;
        }
    }
}

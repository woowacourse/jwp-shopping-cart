package woowacourse.shoppingcart.dto.cartItem;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;

public class CartItemsResponse {

    private List<CartItemInnerResponse> cartItems;

    private CartItemsResponse() {
    }

    public CartItemsResponse(List<CartItem> cartItems) {
        this.cartItems = cartItems.stream()
                .map(CartItemInnerResponse::new)
                .collect(Collectors.toList());
    }

    public List<CartItemInnerResponse> getCartItems() {
        return cartItems;
    }

    public static class CartItemInnerResponse {

        private long id;
        private long productId;
        private String name;
        private int price;
        private int quantity;
        private String imageURL;

        private CartItemInnerResponse() {
        }

        public CartItemInnerResponse(CartItem cartItem) {
            this.id = cartItem.getId();
            this.productId = cartItem.getProduct().getId();
            this.name = cartItem.getProduct().getName();
            this.price = cartItem.getProduct().getPrice();
            this.quantity = cartItem.getQuantity();
            this.imageURL = cartItem.getProduct().getImageUrl();
        }

        public long getId() {
            return id;
        }

        public long getProductId() {
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

package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

public class CartResponse {

    private Long cartItemId;
    private ProductResponse product;
    private int quantity;

    public CartResponse() {
    }

    private CartResponse(final Long cartItemId, final ProductResponse productResponse, final int quantity) {
        this.cartItemId = cartItemId;
        this.product = productResponse;
        this.quantity = quantity;
    }

    public static class ProductResponse {

        private Long id;
        private String name;
        private Integer price;
        private String imageUrl;
        private String description;
        private int stock;

        public ProductResponse() {
        }

        public ProductResponse(final Long id, final String name, final int price, final String imageUrl,
                               final String description, final int stock) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
            this.description = description;
            this.stock = stock;
        }

        public static ProductResponse from(final Product product) {
            return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(),
                    product.getDescription(), product.getStock());
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public Long getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public int getStock() {
            return stock;
        }
    }

    public static CartResponse from(final Cart cart) {
        return new CartResponse(cart.getCartItemId(), ProductResponse.from(cart.getProduct()), cart.getQuantity());
    }


    public Long getCartItemId() {
        return cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductResponse getProduct() {
        return product;
    }
}

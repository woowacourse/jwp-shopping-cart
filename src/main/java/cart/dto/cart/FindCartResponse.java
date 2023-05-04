package cart.dto.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import java.util.List;
import java.util.stream.Collectors;

public class FindCartResponse {

    private final Long id;
    private final List<ProductResponse> products;

    public FindCartResponse(final Long id, final List<ProductResponse> products) {
        this.id = id;
        this.products = products;
    }

    public static FindCartResponse from(final Cart cart) {
        final List<ProductResponse> productResponses = cart.getCartProducts()
                .getCartProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());

        return new FindCartResponse(cart.getCartId().getValue(), productResponses);
    }

    public Long getId() {
        return id;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    private static class ProductResponse {

        private final Long id;
        private final Long productId;
        private final String name;
        private final String image;
        private final int price;

        private ProductResponse(final Long id, final Long productId, final String name, final String image,
                final int price) {
            this.id = id;
            this.productId = productId;
            this.name = name;
            this.image = image;
            this.price = price;
        }

        private static ProductResponse from(final CartProduct cartProduct) {
            return new ProductResponse(
                    cartProduct.getCartProductId().getValue(),
                    cartProduct.getProduct().getProductId().getValue(),
                    cartProduct.getProduct().getProductName(),
                    cartProduct.getProduct().getProductImage().getValue(),
                    cartProduct.getProduct().getProductPrice().getValue()
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

        public String getImage() {
            return image;
        }

        public int getPrice() {
            return price;
        }
    }
}

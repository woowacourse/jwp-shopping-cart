package cart.dto.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import java.util.List;
import java.util.stream.Collectors;

public class AddCartResponse {

    private final Long id;
    private final List<ProductResponse> products;

    public AddCartResponse(final Long id, final List<ProductResponse> products) {
        this.id = id;
        this.products = products;
    }

    public static AddCartResponse from(final Cart cart) {
        final List<ProductResponse> productResponses = cart.getCartProducts()
                .getCartProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return new AddCartResponse(cart.getCartId().getValue(), productResponses);
    }

    public Long getId() {
        return id;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    private static class ProductResponse {

        private final Long id;

        private ProductResponse(final Long id) {
            this.id = id;
        }

        private static ProductResponse from(final CartProduct cartProduct) {
            return new ProductResponse(cartProduct.getProduct().getProductId().getValue());
        }

        public long getId() {
            return id;
        }
    }
}

package cart.dto.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import cart.domain.product.Product;
import java.util.List;
import java.util.stream.Collectors;

public class FindCartResponse {

    private final Long id;
    private final List<ProductResponse> products;

    public FindCartResponse(final Long id, final List<ProductResponse> products) {
        this.id = id;
        this.products = products;
    }

    public static FindCartResponse of(final Cart cart, final List<Product> products) {
        final List<ProductResponse> productResponses = ProductResponse.of(
                cart.getCartProducts().getCartProducts(),
                products);

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

        private static List<ProductResponse> of(final List<CartProduct> cartProducts,
                final List<Product> products) {
            return cartProducts.stream()
                    .map(cartProduct -> toProductResponse(products, cartProduct))
                    .collect(Collectors.toList());
        }

        private static ProductResponse toProductResponse(final List<Product> products, final CartProduct cartProduct) {
            final Product product = filterProduct(products, cartProduct);
            return new ProductResponse(
                    cartProduct.getCartProductId().getValue(),
                    product.getProductId().getValue(),
                    product.getProductName(),
                    product.getProductImage().getValue(),
                    product.getProductPrice().getValue()
            );
        }

        private static Product filterProduct(final List<Product> products, final CartProduct cartProduct) {
            return products.stream()
                    .filter(p -> p.getProductId().equals(cartProduct.getProductId()))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
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

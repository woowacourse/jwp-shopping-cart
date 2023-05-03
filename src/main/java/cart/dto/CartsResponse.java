package cart.dto;

import cart.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CartsResponse {
    private final List<CartResponse> cartResponses;

    public CartsResponse(List<CartResponse> cartResponses) {
        this.cartResponses = cartResponses;
    }

    public static CartsResponse of(List<Product> products, List<Long> cartIds) {
        List<CartResponse> cartResponses = IntStream.range(0, products.size())
                .mapToObj(i -> CartResponse.of(cartIds.get(i), products.get(i)))
                .collect(Collectors.toList());
        return new CartsResponse(cartResponses);
    }

    public List<CartResponse> getCartResponses() {
        return cartResponses;
    }

    public static class CartResponse {
        private final Long id;
        private final String productName;
        private final String productImgUrl;
        private final Integer productPrice;

        public CartResponse(Long id, String productName, String productImgUrl, int productPrice) {
            this.id = id;
            this.productName = productName;
            this.productImgUrl = productImgUrl;
            this.productPrice = productPrice;
        }

        public static CartResponse of(Long cartId, Product product) {
            return new CartResponse(cartId, product.getName(), product.getImgUrl(), product.getPrice());
        }

        public long getId() {
            return id;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductImgUrl() {
            return productImgUrl;
        }

        public Integer getProductPrice() {
            return productPrice;
        }
    }

}

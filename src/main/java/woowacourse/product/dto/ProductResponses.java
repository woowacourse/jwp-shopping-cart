package woowacourse.product.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.product.domain.Product;

public class ProductResponses {

    private List<ProductDetailResponse> products;

    private ProductResponses() {
    }

    public ProductResponses(final List<ProductDetailResponse> products) {
        this.products = products;
    }

    public static ProductResponses from(final List<Product> products) {
        final List<ProductDetailResponse> productDetailRespons = products.stream()
            .map(ProductDetailResponse::from)
            .collect(Collectors.toList());
        return new ProductResponses(productDetailRespons);
    }

    public List<ProductDetailResponse> getProducts() {
        return products;
    }

    public static class ProductDetailResponse {
        private Long id;
        private String name;
        private int price;
        private int stock;
        private String imageURL;

        private ProductDetailResponse() {
        }

        public ProductDetailResponse(final Long id, final String name, final int price, final int stock, final String imageURL) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.imageURL = imageURL;
        }

        public static ProductDetailResponse from(final Product product) {
            return new ProductDetailResponse(
                product.getId(),
                product.getName(),
                product.getPrice().getValue(),
                product.getStock().getValue(),
                product.getImageURL()
            );
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public String getImageURL() {
            return imageURL;
        }
    }
}

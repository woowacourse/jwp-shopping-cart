package woowacourse.product.dto;

import woowacourse.product.domain.Product;

public class ProductResponse {

    private InnerProductResponse product;

    private ProductResponse() {
    }

    public ProductResponse(final InnerProductResponse product) {
        this.product = product;
    }

    public static ProductResponse from(final Product product) {
        return new ProductResponse(InnerProductResponse.from(product));
    }

    public InnerProductResponse getProduct() {
        return product;
    }

    public static class InnerProductResponse {

        private Long id;
        private String name;
        private int price;
        private int stock;
        private String imageURL;

        private InnerProductResponse() {
        }

        public InnerProductResponse(final Long id, final String name, final int price, final int stock,
            final String imageURL) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.imageURL = imageURL;
        }

        public static InnerProductResponse from(final Product product) {
            return new InnerProductResponse(
                product.getId(), product.getName(), product.getPrice().getValue(),
                product.getStock().getValue(), product.getImageURL()
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

package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private ProductResponseNested product;

    private ProductResponse() {
    }

    public ProductResponse(final ProductResponseNested product) {
        this.product = product;
    }

    public static ProductResponse from(final Product product) {
        return new ProductResponse(ProductResponseNested.from(product));
    }

    public ProductResponseNested getProduct() {
        return product;
    }

    public static class ProductResponseNested {

        private Long id;
        private String name;
        private Integer price;
        private Integer stock;
        private String imageURL;

        private ProductResponseNested() {
        }

        public ProductResponseNested(final Long id, final String name, final Integer price, final Integer stock,
                                     final String imageURL) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.imageURL = imageURL;
        }

        public static ProductResponseNested from(final Product product) {
            return new ProductResponseNested(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    product.getImageUrl()
            );
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getPrice() {
            return price;
        }

        public Integer getStock() {
            return stock;
        }

        public String getImageURL() {
            return imageURL;
        }
    }
}

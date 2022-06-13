package woowacourse.shoppingcart.dto.product;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.product.Product;

public class ProductsResponse {

    private List<ProductsInnerResponse> products;

    private ProductsResponse() {
    }

    public ProductsResponse(List<Product> products) {
        this.products = products.stream()
                .map(ProductsInnerResponse::new)
                .collect(Collectors.toList());
    }

    public List<ProductsInnerResponse> getProducts() {
        return products;
    }

    public static class ProductsInnerResponse {

        private long id;
        private String name;
        private int price;
        private int stock;
        private String imageURL;

        private ProductsInnerResponse() {
        }

        private ProductsInnerResponse(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.stock = product.getStock();
            this.imageURL = product.getImageUrl();
        }

        public long getId() {
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

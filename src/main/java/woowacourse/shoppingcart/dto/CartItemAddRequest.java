package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class CartItemAddRequest {

    @NotNull
    private Long id;
    @NotNull
    private Integer quantity;

    public CartItemAddRequest() {
    }

    public CartItemAddRequest(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public static class CartItemResponse {

        private ProductResponse product;
        private int quantity;

        public CartItemResponse() {
        }

        public CartItemResponse(CartItem cartItem) {
            this.product = new ProductResponse(cartItem.getProduct());
            this.quantity = cartItem.getQuantity();
        }

        public ProductResponse getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public static class ProductResponse {

        private long id;
        private String name;
        private int price;
        private String imageUrl;
        private int stock;

        public ProductResponse() {
        }

        public ProductResponse(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.imageUrl = product.getImageUrl();
            this.stock = product.getStock();
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

        public String getImageUrl() {
            return imageUrl;
        }

        public int getStock() {
            return stock;
        }
    }
}

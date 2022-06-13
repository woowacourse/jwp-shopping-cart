package woowacourse.shoppingcart.dto;


import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {
    public Long id;
    public Product product;
    public int quantity;

    public CartResponse() {
    }

    public CartResponse(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public static CartResponse from(final Cart cart) {
        return new CartResponse(cart.getId(), new Product(cart.getProductId(), cart.getName(), cart.getPrice(),
                cart.getImageUrl()), cart.getQuantity());
    }

    public static class Product {
        public Long productId;
        public String name;
        public int price;

        public String imageUrl;

        public Product() {
        }

        public Product(Long productId, String name, int price, String imageUrl) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
        }

        public Long getProductId() {
            return productId;
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

    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}

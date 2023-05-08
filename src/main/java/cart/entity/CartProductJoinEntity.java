package cart.entity;

public class CartProductJoinEntity {

    private final Long cartId;
    private final Long productId;
    private final String productName;
    private final String productImgUrl;
    private final int productPrice;

    private CartProductJoinEntity(final Long cartId, final Long productId,
                                 final String productName, final String productImgUrl,
                                 final int productPrice) {
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.productImgUrl = productImgUrl;
        this.productPrice = productPrice;
    }

    public static class Builder {

        private Long cartId;
        private Long productId;
        private String productName;
        private String productImgUrl;
        private int productPrice;

        public Builder cartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productImgUrl(String productImgUrl) {
            this.productImgUrl = productImgUrl;
            return this;
        }

        public Builder productPrice(int productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public CartProductJoinEntity build() {
            return new CartProductJoinEntity(cartId, productId, productName, productImgUrl, productPrice);
        }
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public int getProductPrice() {
        return productPrice;
    }
}

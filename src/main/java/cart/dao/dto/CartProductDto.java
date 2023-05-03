package cart.dao.dto;

public class CartProductDto {

    private final long cartId;
    private final String productName;
    private final int price;
    private final String imgUrl;

    public CartProductDto(long cartId, String productName, int price, String imgUrl) {
        this.cartId = cartId;
        this.productName = productName;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public static class Builder {
        private long cartId;
        private String productName;
        private int price;
        private String imgUrl;

        public Builder id(long id) {
            this.cartId = id;
            return this;
        }

        public Builder productName(String name) {
            this.productName = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder imgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        public CartProductDto build() {
            return new CartProductDto(cartId, productName, price, imgUrl);
        }
    }

    public long getCartId() {
        return cartId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}

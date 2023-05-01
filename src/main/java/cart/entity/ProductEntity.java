package cart.entity;

public class ProductEntity {

    private Long productId;
    private String name;
    private String imgUrl;
    private int price;

    private ProductEntity(Long productId, String name, String imgUrl, int price) {
        this.productId = productId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static class Builder {

        private Long productId;
        private String name;
        private String imgUrl;
        private int price;

        public Builder productId(final Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder imgUrl(final String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        public Builder price(final int price) {
            this.price = price;
            return this;
        }

        public ProductEntity build() {
            return new ProductEntity(productId, name, imgUrl, price);
        }
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }
}

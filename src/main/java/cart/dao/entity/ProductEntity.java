package cart.dao.entity;

public class ProductEntity {

    private Long id;
    private String name;
    private String imgUrl;
    private int price;

    private ProductEntity(Long id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static class Builder {

        private Long id;
        private String name;
        private String imgUrl;
        private int price;

        public Builder id(final Long id) {
            this.id = id;
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
            return new ProductEntity(id, name, imgUrl, price);
        }
    }

    public Long getId() {
        return id;
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

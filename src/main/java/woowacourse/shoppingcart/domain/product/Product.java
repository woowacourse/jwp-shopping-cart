package woowacourse.shoppingcart.domain.product;

public class Product {

    private final Long id;
    private final ProductName productName;
    private final Price price;
    private final Stock stock;
    private final String imageUrl;

    public Product(final Long id, final ProductName productName, final Price price, final Stock stock, final String imageUrl) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public boolean isStockAvailable(int quantity) {
        return stock.isRemain(quantity);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return productName.getName();
    }

    public int getPrice() {
        return price.getPrice();
    }

    public int getStock() {
        return stock.getStock();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private ProductName productName;
        private Price price;
        private Stock stock;
        private String imageUrl;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = new ProductName(productName);
            return this;
        }

        public Builder price(int price) {
            this.price = new Price(price);
            return this;
        }

        public Builder stock(int stock) {
            this.stock = new Stock(stock);
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(id, productName, price, stock, imageUrl);
        }
    }
}

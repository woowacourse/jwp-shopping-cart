package woowacourse.shoppingcart.domain.product;

public class Product {

    private final Long id;
    private final ProductName productName;
    private final Price price;
    private final Stock stock;
    private final String imageUrl;

    public Product(final Long id, final String name, final int price, final int stock, final String imageUrl) {
        this.id = id;
        this.productName = new ProductName(name);
        this.price = new Price(price);
        this.stock = new Stock(stock);
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final int stock, final String imageUrl) {
        this(null, name, price, stock, imageUrl);
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
}

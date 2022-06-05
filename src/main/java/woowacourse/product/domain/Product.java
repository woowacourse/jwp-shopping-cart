package woowacourse.product.domain;

import java.util.Objects;

public class Product {

    private final Long id;
    private String name;
    private Price price;
    private Stock stock;
    private String imageURL;

    public Product(final Long id, final String name, final Price price, final Stock stock, final String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public Product(final String name, final Price price, final Stock stock, final String imageURL) {
        this(null, name, price, stock, imageURL);
    }

    public void checkProductAvailableForPurchase(final int quantity) {
        stock.checkAvailableForPurchase(quantity);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Stock getStock() {
        return stock;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Product product = (Product)o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

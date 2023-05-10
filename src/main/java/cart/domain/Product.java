package cart.domain;

public class Product {

    private final ProductName name;
    private final String imgUrl;
    private final Price price;

    public Product(final ProductName name, final String imgUrl, final Price price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public String getNameToString() {
        return name.getName();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPriceToValue() {
        return price.getValue();
    }
}

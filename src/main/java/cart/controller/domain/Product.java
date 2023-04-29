package cart.controller.domain;

public class Product {

    private final Name name;
    private final String imgUrl;
    private final Price price;

    private Product(final Name name, final String imgUrl, final Price price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static Product createBy(final String name, final String imgUrl, final int price) {
        Name wrappedName = new Name(name);
        Price wrappedPrice = new Price(price);
        return new Product(wrappedName, imgUrl, wrappedPrice);
    }

    public String getName() {
        return name.getName();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price.getPrice();
    }
}

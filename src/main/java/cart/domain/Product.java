package cart.domain;

public class Product {

    private Long id;
    private Name name;
    private String imgUrl;
    private Price price;

    public Product(final Long id, final Name name, final String imgUrl, final Price price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static Product from(final Long id, final String name, final String imgUrl, final int price) {
        return new Product(id, new Name(name), imgUrl, new Price(price));
    }
}

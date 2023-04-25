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

    public void edit(final String name, final String imgUrl, final int price) {
        this.name.edit(name);
        this.imgUrl = imgUrl;
        this.price.edit(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name.getName();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return this.price.getPrice();
    }
}

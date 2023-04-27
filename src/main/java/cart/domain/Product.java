package cart.domain;


import java.util.Objects;

public class Product {

    private Long id;
    private Name name;
    private String imgUrl;
    private Price price;

    private Product(final Long id, final Name name, final String imgUrl, final Price price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static Product from(final Long id, final String name, final String imgUrl, final int price) {
        return new Product(id, new Name(name), imgUrl, new Price(price));
    }

    public static Product from(final String name, final String imgUrl, final int price) {
        return Product.from(null, name, imgUrl, price);
    }

    public void edit(final String name, final String imgUrl, final int price) {
        this.name.edit(name);
        this.imgUrl = imgUrl;
        this.price.edit(price);
    }

    public void setId(final Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

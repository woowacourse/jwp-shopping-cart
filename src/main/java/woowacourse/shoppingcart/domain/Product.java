package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.Objects;

public class Product {

    private static final NameValidation productNameValidation = new ProductNameValidationImpl();
    private Id id;
    private Name name;
    private Price price;
    private ImageURL imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        this.id = Id.from(id, "상품");
        productNameValidation.validateName(name);
        this.name = new Name(name);
        this.price = new Price(price);
        this.imageUrl = new ImageURL(imageUrl);
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public boolean isSameId(Long productId) {
        return this.id.getId() == productId.longValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl);
    }

    public String getName() {
        return name.getName();
    }

    public int getPrice() {
        return price.getPrice();
    }

    public String getImageUrl() {
        return imageUrl.getUrl();
    }

    public Long getId() {
        return id.getId();
    }
}

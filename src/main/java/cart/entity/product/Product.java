package cart.entity.product;

import cart.domain.Id;
import cart.domain.ImgUrl;
import cart.domain.Name;
import cart.domain.Price;

import java.util.Objects;

public class Product {
    private final Id id;
    private final Name name;
    private final ImgUrl imgUrl;
    private final Price price;

    public Product(Long id, String name, String imgUrl, Integer price) {
        this.id = Id.of(id);
        this.name = Name.of(name);
        this.imgUrl = ImgUrl.of(imgUrl);
        this.price = Price.of(price);
    }

    public Product(String name, String imgUrl, Integer price) {
        this(null, name, imgUrl, price);
    }

    public Long getId() {
        return id.getId();
    }

    public String getName() {
        return name.getName();
    }

    public String getImgUrl() {
        return imgUrl.getImgUrl();
    }

    public Integer getPrice() {
        return price.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

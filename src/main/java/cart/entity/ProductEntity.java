package cart.entity;

import cart.domain.ImgUrl;
import cart.domain.Name;
import cart.domain.Price;

import java.util.Objects;

public class ProductEntity {

    private final Long id;
    private final Name name;
    private final ImgUrl imgUrl;
    private final Price price;

    public ProductEntity(Long id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = new Name(name);
        this.imgUrl = new ImgUrl(imgUrl);
        this.price = new Price(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImgUrl() {
        return imgUrl.getImgUrl();
    }

    public int getPrice() {
        return price.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

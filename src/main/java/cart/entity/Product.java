package cart.entity;

import cart.entity.vo.Id;
import cart.entity.vo.Image;
import cart.entity.vo.Name;
import cart.entity.vo.Price;

public class Product {

    private final Id id;
    private final Name name;
    private final Price price;
    private final Image image;

    public Product(Id id, Name name, Price price, Image image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(Long id, String name, Integer price, String image) {
        this.id = new Id(id);
        this.name = new Name(name);
        this.price = new Price(price);
        this.image = new Image(image);
    }

    public Long getId() {
        return id.value();
    }

    public String getName() {
        return name.value();
    }

    public Integer getPrice() {
        return price.value();
    }

    public String getImage() {
        return image.value();
    }
}

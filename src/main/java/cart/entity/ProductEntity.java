package cart.entity;

import java.util.Objects;

public class ProductEntity {
    private final Integer id;
    private final String name;
    private final String image;
    private final Integer price;

    public ProductEntity(final Integer id, final String name, final String image, final Integer price) {
        validate(name, image, price);
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    private void validate(final String name, final String image, final Integer price) {
        if (Objects.isNull(name) || Objects.isNull(image) || Objects.isNull(price)) {
            throw new IllegalArgumentException("Product 의 name, image, price 는 null 을 허용하지 않습니다.");
        }
    }

    public ProductEntity(final String name, final String image, final Integer price) {
        this(null, name, image, price);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}

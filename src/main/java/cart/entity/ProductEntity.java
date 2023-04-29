package cart.entity;

import java.util.Objects;

public class ProductEntity {

    private Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductEntity(final String name, final String imageUrl, final int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public ProductEntity(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        Objects.requireNonNull(id, "id가 null입니다.");
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}

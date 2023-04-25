package cart.entity.product;

import java.util.Objects;

public class ProductEntity {

    private Long id;
    private Name name;
    private ImageUrl imageUrl;
    private Price price;
    private Description description;

    public ProductEntity(final Long id, final String name, final String imageUrl, final Integer price,
        final String description) {
        this.id = id;
        this.name = new Name(name);
        this.imageUrl = new ImageUrl(imageUrl);
        this.price = new Price(price);
        this.description = new Description(description);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getPrice() {
        return price.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }
}

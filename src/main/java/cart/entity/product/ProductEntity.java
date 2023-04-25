package cart.entity.product;

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

    public Integer getPrice() {
        return price.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }
}

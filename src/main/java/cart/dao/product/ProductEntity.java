package cart.dao.product;

public class ProductEntity {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductEntity(Long id, String name, Integer price, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

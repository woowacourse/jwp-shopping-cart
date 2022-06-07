package woowacourse.shoppingcart.infra.dao.entity;

public class ProductEntity {
    private final Long id;
    private final String name;
    private final long price;
    private final String imageUrl;

    public ProductEntity(final Long id, final String name, final long price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductEntity(final String name, final long price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public ProductEntity assignId(final long id) {
        return new ProductEntity(id, this.name, this.price, this.imageUrl);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

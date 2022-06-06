package woowacourse.shoppingcart.domain.product;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private ThumbnailImage thumbnailImage;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final ThumbnailImage thumbnailImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnailImage = thumbnailImage;

    }

    public Product(final String name, final int price, final ThumbnailImage thumbnailImage) {
        this(null, name, price, thumbnailImage);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImage.getUrl();
    }

    public String getThumbnailImageAlt() {
        return thumbnailImage.getAlt();
    }

    public Long getId() {
        return id;
    }
}

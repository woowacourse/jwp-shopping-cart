package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private ThumbnailImage thumbnailImage;

    public Product() {
    }

    public Product(Long id, String name, int price, int stockQuantity, ThumbnailImage thumbnailImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
    }

    public Product(String name, int price, int stockQuantity, ThumbnailImage thumbnailImage) {
        this(null, name, price, stockQuantity, thumbnailImage);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public ThumbnailImage getThumbnailImage() {
        return thumbnailImage;
    }
}

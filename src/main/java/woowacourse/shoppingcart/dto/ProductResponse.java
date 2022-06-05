package woowacourse.shoppingcart.dto;

public class ProductResponse {
    private long id;
    private String name;
    private int price;
    private Integer stockQuantity;
    private ThumbnailImageDto thumbnailImage;

    public ProductResponse() {
    }

    public ProductResponse(long id, String name, int price, String url) {
        this(id, name, price, null, new ThumbnailImageDto(url));
    }

    public ProductResponse(long id, String name, int price, Integer stockQuantity,
        ThumbnailImageDto thumbnailImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public ThumbnailImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}

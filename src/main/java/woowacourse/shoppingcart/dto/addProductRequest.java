package woowacourse.shoppingcart.dto;

public class addProductRequest {

    private String name;
    private int price;
    private int stockQuantity;
    private ImageDto thumbnailImage;

    public addProductRequest() {
    }

    public addProductRequest(String name, int price, int stockQuantity, ImageDto thumbnailImage) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
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

    public ImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}

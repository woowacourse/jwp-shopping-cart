package cart.dto;

public class CartResponse {

    private final long productId;
    private final String imgUrl;
    private final String name;
    private final int price;

    public CartResponse(final long productId, final String imgUrl, final String name, final int price) {
        this.productId = productId;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

package cart.dao.dto;

public class CartProductResultMap {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imgUrl;
    private final int count;

    public CartProductResultMap(final Long id, final Long productId, final String name, final int price, final String imgUrl, final int count) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getCount() {
        return count;
    }
}

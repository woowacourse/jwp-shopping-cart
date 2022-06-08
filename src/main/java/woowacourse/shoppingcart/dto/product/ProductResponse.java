package woowacourse.shoppingcart.dto.product;

public class ProductResponse {

    private final int id;
    private final String name;
    private final int price;
    private final String thumbnail;
    private final int quantity;

    public ProductResponse(int id, String name, int price, String thumbnail, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }
}

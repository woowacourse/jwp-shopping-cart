package cart.dto.response;

public class CartResponse {
    private final int id;
    private final String name;
    private final String imgUrl;
    private final int price;
    private final int count;

    public CartResponse(int id, String name, String imgUrl, int price, int count) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}

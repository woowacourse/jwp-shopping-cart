package cart.dto.response;

public class CartResponse {
    private int id;
    private String name;
    private String imgUrl;
    private int price;

    public CartResponse() {
    }

    public CartResponse(int id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
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
}

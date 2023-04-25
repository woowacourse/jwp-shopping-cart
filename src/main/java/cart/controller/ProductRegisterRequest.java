package cart.controller;

public class ProductRegisterRequest {

    private String name;
    private int price;
    private String imageUrl;

    public ProductRegisterRequest() {
    }

    public ProductRegisterRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

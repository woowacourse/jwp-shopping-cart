package woowacourse.shoppingcart.dto;

public class ProductResponse {

    private final int id;
    private final String name;
    private final int price;
    private final String thumbnail;
    private final boolean isStored;

    public ProductResponse(int id, String name, int price, String thumbnail, boolean isStored) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.isStored = isStored;
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

    public boolean getIsStored() {
        return isStored;
    }
}

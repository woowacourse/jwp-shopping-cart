package dto;

public class ProductRequestDto {
    private final String name;
    private final String imgURL;
    private final int price;

    public ProductRequestDto(String name, String imgURL, int price) {
        this.name = name;
        this.imgURL = imgURL;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public int getPrice() {
        return price;
    }
}

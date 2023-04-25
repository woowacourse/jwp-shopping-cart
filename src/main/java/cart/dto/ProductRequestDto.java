package cart.dto;

public class ProductRequestDto {
    private String name;
    private String imgUrl;
    private int price;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String imgUrl, int price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
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

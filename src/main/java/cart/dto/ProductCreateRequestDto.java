package cart.dto;

public class ProductCreateRequestDto {

    private String name;
    private int price;
    private String imgUrl;

    public ProductCreateRequestDto() {
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
}

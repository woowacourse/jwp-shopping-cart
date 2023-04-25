package cart.dto;

public class ProductEditRequestDto {

    private Long id;
    private String name;
    private int price;
    private String imgUrl;

    public ProductEditRequestDto() {

    }

    public Long getId() {
        return id;
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

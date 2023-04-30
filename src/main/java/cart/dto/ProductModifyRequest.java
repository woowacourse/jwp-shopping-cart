package cart.dto;

import javax.validation.constraints.NotBlank;

public class ProductModifyRequest {

    private String name;

    private int price;

    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imgUrl;

    private ProductModifyRequest() {
    }

    public ProductModifyRequest(String name, int price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
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

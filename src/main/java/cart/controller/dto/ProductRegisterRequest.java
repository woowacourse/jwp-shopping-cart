package cart.controller.dto;

import javax.validation.constraints.NotBlank;

public class ProductRegisterRequest {

    private String name;

    private int price;

    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imgUrl;

    private ProductRegisterRequest() {
    }

    public ProductRegisterRequest(final String imgUrl, final String name, final int price) {
        this.imgUrl = imgUrl;
        this.name = name;
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

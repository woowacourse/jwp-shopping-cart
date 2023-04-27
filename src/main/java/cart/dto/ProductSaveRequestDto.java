package cart.dto;

import javax.validation.constraints.NotNull;

public class ProductSaveRequestDto {

    @NotNull(message = "상품 이름은 비어있으면 안됩니다.")
    private String name;
    @NotNull(message = "상품 이미지는 비어있으면 안됩니다.")
    private String image;
    @NotNull(message = "상품 가격은 비어있으면 안됩니다.")
    private Integer price;

    public ProductSaveRequestDto() {
    }

    public ProductSaveRequestDto(String name, String image, Integer price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}

package cart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDto {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "이미지는 공백일 수 없습니다.")
    private String image;

    @NotNull(message = "가격은 Null일 수 없습니다.")
    private Integer price;


    public ProductDto() {
    }

    public ProductDto(final String name, final String image, final Integer price) {
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

    public int getPrice() {
        return price;
    }

}

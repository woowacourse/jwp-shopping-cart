package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private Integer price;

    private String imageUrl;
    private String description;

    private ProductRequest() {
    }

    public ProductRequest(String name, Integer price, String imageUrl, String description) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}

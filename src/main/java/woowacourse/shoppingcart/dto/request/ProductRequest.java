package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank(message = "제품 이름은 팔수 항목입니다.")
    private String name;

    @NotNull(message = "제품 가격은 팔수 항목입니다.")
    @Min(value = 0, message = "가격은 0원 미만일 수 없습니다.")
    private Integer price;

    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
}

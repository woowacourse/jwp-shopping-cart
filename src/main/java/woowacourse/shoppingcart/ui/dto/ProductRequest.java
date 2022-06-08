package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ProductRequest {

    @NotEmpty(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @Min(value = 0, message = "가격은 0 미만이 될 수 없습니다.")
    private Integer price;

    @NotEmpty(message = "이미지 URL은 비어있을 수 없습니다.")
    private String imageUrl;

    @Size(max = 255)
    private String description;

    private ProductRequest() {
        this(null, null, null, null);

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

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}

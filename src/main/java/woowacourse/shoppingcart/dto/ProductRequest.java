package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public class ProductRequest {

    @Length(max = 255)
    private String name;

    @Min(value = 0, message = "잘못된 가격 형식입니다.")
    private Integer price;

    @Length(max = 255)
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

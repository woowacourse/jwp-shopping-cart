package woowacourse.shoppingcart.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductRequest {
    @NotBlank(message = "상품명을 입력해주세요.")
    @Length(max = 255, message = "상품명은 255자 이하여야 합니다.")
    private String name;

    @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
    private int price;

    private String thumbnail;

    private ProductRequest() {
    }

    public ProductRequest(String name, int price, String thumbnail) {
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}

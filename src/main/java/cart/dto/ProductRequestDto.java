package cart.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class ProductRequestDto {

    @Length(max = 255, message = "상품명은 영문기준 255자 이하입니다.")
    private String name;
    @Length(max = 8000, message = "URL은 영문기준 8000자 이하입니다.")
    private String imgUrl;
    @Range(min = 0, max = Integer.MAX_VALUE, message = "가격은 0원부터 21억원 미만입니다.")
    private int price;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String imgUrl, int price) {

        this.name = name;
        this.imgUrl = imgUrl;
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

package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import cart.dao.entity.ProductEntity;
import org.hibernate.validator.constraints.Length;

public class ProductModifyRequest {

    @Length(min = 1, max = 50, message = "이름은 1글자 이상 50글자 이하여야합니다.")
    private String name;

    @Positive(message = "가격은 0보다 커야합니다.")
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

    public ProductEntity toEntityBy(long id) {
        return new ProductEntity.Builder()
                .id(id)
                .name(name)
                .price(price)
                .imgUrl(imgUrl)
                .build();
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

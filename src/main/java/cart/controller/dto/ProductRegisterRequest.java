package cart.controller.dto;

import cart.dao.entity.ProductEntity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class ProductRegisterRequest {

    @Length(min = 1, max = 50, message = "이름은 1글자 이상 50글자 이하여야합니다.")
    private String name;

    @Positive(message = "가격은 0보다 커야합니다.")
    private int price;

    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imgUrl;

    public ProductRegisterRequest(final String imgUrl, final String name, final int price) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public ProductEntity toEntity() {
        return new ProductEntity.Builder()
                .name(name)
                .imgUrl(imgUrl)
                .price(price)
                .build();
    }

    public ProductEntity toEntityBy(long id) {
        return new ProductEntity.Builder()
                .name(name)
                .imgUrl(imgUrl)
                .price(price)
                .id(id)
                .build();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

package cart.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RequestCreateProductDto {

    @NotEmpty(message = "상품 이름이 입력되지 않았습니다.")
    private String name;
    @NotNull(message = "가격이 입력되지 않았습니다.")
    private Integer price;
    @NotEmpty(message = "상품 이미지 주소가 입력되지 않았습니다.")
    private String image;

    public RequestCreateProductDto() {
    }

    public RequestCreateProductDto(final String name, final Integer price, final String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}

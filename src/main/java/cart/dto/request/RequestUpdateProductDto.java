package cart.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RequestUpdateProductDto {

    @NotNull(message = "식별자가 입력되지 않았습니다.")
    private final Long id;
    @NotEmpty(message = "상품 이름이 입력되지 않았습니다.")
    private final String name;
    @NotNull(message = "가격이 입력되지 않았습니다.")
    private final Integer price;
    @NotEmpty(message = "상품 이미지 주소가 입력되지 않았습니다.")
    private final String image;

    public RequestUpdateProductDto(final Long id, final String name, final Integer price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
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

package cart.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class RequestProductDto {

    @NotBlank(message = "상품 이름을 입력해주세요.")
    @Size(max = 10, message = "상품 이름은 10 글자를 넘을 수 없습니다.")
    private final String name;
    @NotBlank(message = "상품 이미지 url을 입력해주세요.")
    private final String image;
    @NotNull(message = "상품 가격을 입력해주세요.")
    @PositiveOrZero(message = "상품의 가격은 0보다 작을 수 없습니다.")
    private final int price;
    
    public RequestProductDto(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public String getImage() {
        return image;
    }
    
    public int getPrice() {
        return price;
    }
}

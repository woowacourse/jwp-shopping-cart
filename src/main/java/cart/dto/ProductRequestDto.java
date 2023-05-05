package cart.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class ProductRequestDto {

    @NotBlank(message = "상품의 이름은 비어있을 수 없습니다.")
    @Length(min = 1, max = 25, message = "상품의 이름은 {min}자 ~ {max}자여야 합니다")
    private final String name;

    @NotBlank(message = "상품의 이미지 url은 비어있을 수 없습니다.")
    private final String image;

    @NotNull(message = "상품의 가격은 비어있을 수 없습니다.")
    @Range(min = 0, max = 100_000_000, message = "상품 가격은 {min} ~ {max}원까지 가능합니다.")
    private final BigDecimal price;

    public ProductRequestDto(String name, String image, BigDecimal price) {
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

    public BigDecimal getPrice() {
        return price;
    }

}

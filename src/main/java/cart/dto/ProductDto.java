package cart.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private Long id;
    @NotNull
    @NotBlank(message = "상품 이름은 빈 문자열일 수 없습니다")
    @Length(min = 1, max = 30, message = "상품 이름은 최소 1, 최대 30글자입니다.")
    private String name;
    @NotNull
    @NotBlank(message = "이미지 URL은 빈 문자열일 수 없습니다")
    @Length(max = 1000, message = "이미지 URL 길이가 너무 깁니다.")
    private String image;
    @PositiveOrZero
    @Max(value = Integer.MAX_VALUE, message = "상품 금액이 너무 큽니다.")
    private int price;
}

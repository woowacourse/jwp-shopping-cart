package cart.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@RequiredArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "상품 이름은 비어있으면 안 됩니다.")
    @Length(
            max = 20,
            message = "상품 이름의 길이는 {max}자리 보다 작아야 합니다."
    )
    private final String name;

    @Positive(message = "상품의 가격은 0보다 커야 합니다.")
    @Max(
            value = 10_000_000,
            message = "상품의 가격은 {value}보다 작아야 합니다."
    )
    private final int price;

    @NotBlank(message = "상품의 이미지는 비어있으면 안 됩니다.")
    private final String imageUrl;
}

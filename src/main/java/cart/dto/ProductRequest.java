package cart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class ProductRequest {
    @NotBlank(message = "[ERROR] 상품 이름을 입력해주세요.")
    @Length(message = "[ERROR] 상품 이름은 255자까지 입력가능합니다.", max = 255)
    private final String name;
    
    @NotBlank(message = "[ERROR] 이미지 URL을 입력해주세요.")
    @Length(message = "[ERROR] 이미지 URL은 255자까지 입력가능합니다.", max = 255)
    private final String imageUrl;
    
    @NotNull(message = "[ERROR] 가격을 입력해주세요.")
    @Max(message = "[ERROR] 가격의 최대 금액은 1000만원입니다.", value = 10_000_000)
    @Min(message = "[ERROR] 가격의 최소 금액은 1원입니다.", value = 1)
    private final Integer price;
}

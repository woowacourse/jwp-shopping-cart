package cart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class ProductRequest {
    @NotBlank(message = "[ERROR] 상품 이름을 입력해주세요.")
    private final String name;
    
    @NotBlank(message = "[ERROR] 이미지 URL을 입력해주세요.")
    private final String imageUrl;
    
    @NotNull(message = "[ERROR] 가격을 입력해주세요.")
    private final Integer price;
}

package cart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class CartProductRequest {
    @NotNull(message = "[ERROR] 상품 아이디를 입력해주세요.")
    private Long productId;
}

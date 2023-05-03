package cart.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartSaveRequest {

    @NotNull(message = "상품 ID는 공백일 수 없습니다.")
    @JsonProperty("itemId")
    private Long id;
}

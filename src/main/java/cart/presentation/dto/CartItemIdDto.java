package cart.presentation.dto;

import javax.validation.constraints.Positive;

public class CartItemIdDto {

    @Positive(message = "상품 id는 0보다 커야 합니다.")
    private Integer id;

    public CartItemIdDto() {
    }

    public CartItemIdDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

package cart.dto;

import javax.validation.constraints.Positive;

public class CartRequestDto {

    @Positive
    private Long id;

    public CartRequestDto() {
    }

    public CartRequestDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}

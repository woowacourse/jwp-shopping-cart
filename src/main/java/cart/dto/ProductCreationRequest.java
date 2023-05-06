package cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ProductCreationRequest {

    @NotBlank
    private final String name;

    @NotBlank
    private final String image;

    @NotNull
    private final Integer price;
}

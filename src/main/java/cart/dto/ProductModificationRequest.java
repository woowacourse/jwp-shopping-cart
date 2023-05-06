package cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ProductModificationRequest {

    @NotNull
    private final Long id;

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String image;

    @NotNull
    private final Integer price;
}

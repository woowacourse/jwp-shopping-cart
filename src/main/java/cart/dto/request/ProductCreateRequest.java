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
    @NotBlank
    @Length(min = 1, max = 10)
    private final String name;

    @Positive
    @Max(10_000_000)
    private final int price;

    @NotBlank
    private final String imageUrl;
}

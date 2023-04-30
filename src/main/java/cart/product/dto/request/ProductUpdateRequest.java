package cart.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

    @NotNull
    private Long id;
    @NotNull
    private String name;
    @JsonProperty("image-url")
    @NotNull
    private String imageUrl;
    @NotNull
    private Integer price;
}

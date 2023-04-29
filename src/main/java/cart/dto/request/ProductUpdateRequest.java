package cart.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
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

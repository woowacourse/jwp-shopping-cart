package cart.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private String name;
    @JsonProperty("image-url")
    private String imageUrl;
    private int price;
}

package cart.dto.item;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveRequest {

    @NotBlank(message = "상품명은 공백일 수 없습니다.")
    @Size(max = 30, message = "상품명의 길이는 30자 이하여야합니다.")
    private String name;

    @NotBlank(message = "이미지 url은 공백일 수 없습니다.")
    @JsonProperty("image-url")
    private String imageUrl;

    @NotNull(message = "가격은 공백일 수 없습니다.")
    @Min(value = 0, message = "가격은 최소 0원 이상이어야합니다.")
    @Max(value = 1000000, message = "가격은 최대 100만원 이하여야합니다.")
    private Integer price;
}

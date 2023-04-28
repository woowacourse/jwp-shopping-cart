package cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductRequest {

    @Getter
    @NoArgsConstructor
    public static class AddDto {

        @NotNull
        private String name;
        @JsonProperty("image-url")
        @NotNull
        private String imageUrl;
        @NotNull
        private Integer price;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateDto {

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
}

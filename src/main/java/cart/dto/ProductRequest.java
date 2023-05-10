package cart.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductRequest {

    @Getter
    @NoArgsConstructor
    public static class AddDto {

        @NotNull
        private String name;
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
        @NotNull
        private String imageUrl;
        @NotNull
        private Integer price;
    }
}

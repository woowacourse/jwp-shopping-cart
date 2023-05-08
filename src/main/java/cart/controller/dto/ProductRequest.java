package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProductRequest {
    @NotBlank(message = "이름이 비어있습니다")
    private String name;
    @NotBlank(message = "이미지가 비어있습니다")
    @Size(max = 2000, message = "이미지는 2000글자 이내여야 합니다")
    @Pattern(regexp = "^(https://|http://).+", message = "이미지가 URL이 아닙니다")
    private String image;
    @NotNull(message = "금액이 비어있습니다")
    private Long price;

    public ProductRequest() {
    }

    public ProductRequest(final String name, final String image, final Long price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Long getPrice() {
        return price;
    }
}

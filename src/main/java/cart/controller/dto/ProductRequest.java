package cart.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductRequest {
    @NotEmpty(message = "이름이 비어있습니다.")
    private String name;
    @NotEmpty(message = "이미지가 비어있습니다.")
    private String image;
    @NotNull(message = "금액이 비어있습니다.")
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

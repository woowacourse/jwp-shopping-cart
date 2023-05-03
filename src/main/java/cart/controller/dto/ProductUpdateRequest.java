package cart.controller.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductUpdateRequest {
    @NotNull(message = "아이디가 비어있습니다.")
    private final Long id;
    @NotBlank(message = "이름이 비어있습니다.")
    private String name;
    @NotBlank(message = "이미지가 비어있습니다.")
    private String image;
    @Range(
            min = 0,
            max = 100000000,
            message = "상품의 가격은 0~100000000 사이의 값이여야 합니다."
    )
    private Long price;

    public ProductUpdateRequest(final Long id, final String name, final String image, final Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getId() {
        return id;
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

package cart.dto;

import javax.validation.constraints.NotNull;

public class ProductUpdateRequestDto {
    @NotNull(message = "상품 id는 비어있으면 안됩니다.")
    private Long id;
    @NotNull(message = "상품 이름은 비어있으면 안됩니다.")
    private String name;
    @NotNull(message = "상품 이미지는 비어있으면 안됩니다.")
    private String image;
    @NotNull(message = "상품 가격은 비어있으면 안됩니다.")
    private Integer price;

    public ProductUpdateRequestDto() {
    }

    public ProductUpdateRequestDto(Long id, String name, String image, Integer price) {
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

    public Integer getPrice() {
        return price;
    }
}

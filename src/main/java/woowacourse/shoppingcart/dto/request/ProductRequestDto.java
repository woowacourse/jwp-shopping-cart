package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductRequestDto {

    @NotBlank(message = "상품명은 비어있을 수 없습니다.")
    @Size(min = 1, max = 254)
    private String name;
    private Integer price;
    private String thumbnailUrl;
    private Integer quantity;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, Integer price, String thumbnailUrl, final Integer quantity) {
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

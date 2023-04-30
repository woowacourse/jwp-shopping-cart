package cart.dto;

import cart.entity.Product;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductModifyRequestDto {

    @NotBlank(message = "상품 이름을 입력해주세요.")
    private String name;

    @Min(value = 0, message = "가격은 0원 이상이여야 합니다.")
    private int price;

    @NotBlank(message = "이미지 url을 입력해주세요.")
    private String imageUrl;

    private ProductModifyRequestDto() {
    }

    public ProductModifyRequestDto(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product toEntity() {
        return new Product.Builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }

}

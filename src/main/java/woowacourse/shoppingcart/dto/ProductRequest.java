package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {

    @NotEmpty(message = "제품명을 입력해주세요.")
    private String name;

    @Min(value = 0, message = "0원 이상의 금액을 입력해주세요.")
    private int price;

    @NotBlank(message = "이미지 주소에는 공백이 허용되지 않습니다.")
    private String imageUrl;

    private ProductRequest() {
    }

    public ProductRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toProduct() {
        return new Product(name, price, imageUrl);
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
}

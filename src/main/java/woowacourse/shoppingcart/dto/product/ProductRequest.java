package woowacourse.shoppingcart.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductRequest {

    @NotEmpty(message = "상품명은 필수 입력 사항입니다.")
    @Size(max = 100, message = "상품명 길이가 올바르지 않습니다. (길이: 100자 이내)")
    private final String name;

    @Min(value = 0, message = "상품 금액은 0원 이상이어야 합니다.")
    private final Integer price;

    @Pattern(regexp = "http.*", message = "상품 이미지 url 형식이 올바르지 않습니다. (형식: http로 시작)")
    @Size(max = 1024, message = "상품 이미지 url 길이가 올바르지 않습니다. (길이: 1024자 이내)")
    private final String imageUrl;

    @Size(max = 225, message = "상세 설명 길이가 올바르지 않습니다. (길이: 255자 이내)")
    private final String description;

    public ProductRequest() {
        this(null, null, null, null);
    }

    public ProductRequest(Product product) {
        this(product.getName(), product.getPrice(), product.getImageUrl(), product.getDescription());
    }

    public ProductRequest(String name, Integer price, String imageUrl, String description) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product toProduct() {
        return new Product(name, price, imageUrl, true, description);
    }

    public String getDescription() {
        return description;
    }
}

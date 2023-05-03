package cart.dto;

import cart.entity.Product;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class ProductRequest {
    @NotNull(message = "이름을 입력해주세요")
    @Size(max = 20, message = "상품 이름은 20자 이내로 입력해야 합니다.")
    private final String name;
    @NotNull(message = "이미지를 입력해주세요")
    @URL(message = "이미지는 url 형식으로 입력해주세요")
    private final String imgUrl;
    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 1000, message = "상품 가격은 최소 1000원 이상이어야 합니다.")
    private final Integer price;

    public ProductRequest(String name, String imgUrl, Integer price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public Product toEntity() {
        return new Product(name, imgUrl, price);
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                '}';
    }
}

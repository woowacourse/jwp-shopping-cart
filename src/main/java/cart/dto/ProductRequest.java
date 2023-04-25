package cart.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ProductRequest {
    @Max(value = 20, message = "상품 이름은 20자 이내로 입력해야 합니다.")
    private final String name;
    private final String imgUrl;
    @Min(value = 1000, message = "상품 가격은 최소 1000원 이상이어야 합니다.")
    private final int price;

    private ProductRequest(String name, String imgUrl, int price) {
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

    public int getPrice() {
        return price;
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

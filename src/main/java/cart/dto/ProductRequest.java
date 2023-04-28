package cart.dto;

import javax.validation.constraints.Min;

public class ProductRequest {
    private final String name;
    private final String imgUrl;
    @Min(value = 0, message = "상품 가격은 음수가 될 수 없습니다.")
    private final int price;

    public ProductRequest(String name, String imgUrl, int price) {
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

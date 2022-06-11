package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductRequest {
    @NotBlank(message = "이름이 비어있을 수 없습니다.")
    private String name;
    @Min(value = 0, message = "상품의 가격은 음수일 수 없습니다.")
    private int price;
    @Min(value = 0, message = "재고가 음수일 수 없습니다.")
    private int stock;
    private String imageURL;

    private ProductRequest() {
    }

    public ProductRequest(String name, int price, int stock, String imageURL) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageURL() {
        return imageURL;
    }
}

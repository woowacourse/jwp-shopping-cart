package cart.service.dto;

import cart.domain.Price;
import cart.domain.Product;
import cart.domain.ProductName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    private int price;

    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imgUrl;

    public ProductRequest(final String imgUrl, final String name, final int price) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public ProductRequest() {
    }

    public Product toProduct() {
        return new Product(new ProductName(name), imgUrl, new Price(price));
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
}

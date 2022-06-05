package woowacourse.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import woowacourse.product.domain.Price;
import woowacourse.product.domain.Product;
import woowacourse.product.domain.Stock;

public class ProductRequest {

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotNull(message = "가격을 입력해 주세요.")
    private Integer price;

    @NotNull(message = "재고를 입력해 주세요.")
    private Integer stock;

    @NotBlank(message = "이미지 URL을 입력해 주세요.")
    private String imageURL;

    private ProductRequest() {
    }

    public ProductRequest(final String name, final Integer price, final Integer stock, final String imageURL) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public Product toProduct() {
        return new Product(name, new Price(price), new Stock(stock), imageURL);
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getImageURL() {
        return imageURL;
    }
}

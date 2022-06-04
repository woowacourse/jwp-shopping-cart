package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ProductRequest {

    private Long id;
    @NotEmpty(message = "이름은 비어있을 수 없습니다.")
    private String name;
    @Min(value = 0, message = "가격은 0 미만이 될 수 없습니다.")
    private Integer price;
    @NotEmpty(message = "이미지 URL은 비어있을 수 없습니다.")
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductRequest(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
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

    public Long getId() {
        return id;
    }
}

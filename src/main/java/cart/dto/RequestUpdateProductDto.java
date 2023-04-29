package cart.dto;

import cart.domain.Product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RequestUpdateProductDto {

    @NotNull(message = "식별자가 입력되지 않았습니다.")
    private Long id;
    @NotEmpty(message = "상품 이름이 입력되지 않았습니다.")
    private String name;
    @NotNull(message = "가격이 입력되지 않았습니다.")
    private Integer price;
    @NotEmpty(message = "상품 이미지 주소가 입력되지 않았습니다.")
    private String image;

    public RequestUpdateProductDto() {
    }

    public RequestUpdateProductDto(final Long id, final String name, final Integer price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product toProduct() {
        return new Product(name, price, image);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}

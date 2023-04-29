package cart.presentation.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class ProductDto {

    private Integer id;
    @NotEmpty(message = "이름은 필수 입력 값입니다.")
    private String name;
    @NotEmpty(message = "url은 필수 입력 값입니다.")
    private String url;
    @Positive(message = "가격은 0보다 커야 합니다.")
    private Integer price;

    public ProductDto() {
    }

    public ProductDto(Integer id, String name, String url, Integer price) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPrice() {
        return price;
    }
}

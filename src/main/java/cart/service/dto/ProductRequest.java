package cart.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequest {

    private Long id;
    @NotBlank(message = "이름이 비어있을 수는 없습니다.")
    private String name;
    @NotBlank(message = "imageUrl이 비어있을 수 없습니다.")
    private String imageUrl;
    @NotNull(message = "가격은 비어있을 수 없습니다.")
    private Integer price;

    private ProductRequest() {
    }

    public ProductRequest(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public ProductRequest(final String name, final String imageUrl, final Integer price) {
        this(null, name, imageUrl, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }
}

package cart.dto;

import cart.repository.entity.ProductEntity;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class ProductDto {

    private Long id;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "이미지 경로는 공백일 수 없습니다.")
    @URL(message = "URL 형식이 아닙니다.")
    private String imageUrl;

    @PositiveOrZero(message = "가격은 10억 이하의 음이 아닌 정수여야 합니다.")
    @Max(value = 1_000_000_000, message = "가격은 10억 이하의 음이 아닌 정수여야 합니다.")
    private int price;

    public ProductDto() {
    }

    public ProductDto(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public ProductDto(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public static ProductDto from(final ProductEntity productEntity) {
        return new ProductDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getImageUrl(),
                productEntity.getPrice());
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

    public int getPrice() {
        return price;
    }
}

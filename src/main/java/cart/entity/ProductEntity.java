package cart.entity;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductEntity {

    private final Long id;

    @NotBlank
    private final String name;

    @NotNull
    @URL
    private final String image;

    @Max(value = 10_000_000, message = "상품 등록은 최대 천만원까지 가능합니다.")
    @Positive
    private final Integer price;

    private ProductEntity(final Long id, final String name, final String image, final Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductEntity of(final String name, final String image, final Integer price) {
        return new ProductEntity(null, name, image, price);
    }

    public static ProductEntity of(final Long id, final String name, final String image, final Integer price) {
        return new ProductEntity(id, name, image, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}

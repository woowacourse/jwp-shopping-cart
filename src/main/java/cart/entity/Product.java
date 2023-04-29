package cart.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class Product {

    private static final int MAX_NAME_LENGTH = 254;
    private static final int MIN_NAME_LENGTH = 1;
    private static final String IMAGE_EXTENSION_FORMAT = ".*\\.(jpg|jpeg|png|webp|avif|gif|svg)$";

    private Long id;
    @Length(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, message = "이름은 0자 초과 255미만이어야 합니다.")
    private String name;
    @Pattern(regexp = IMAGE_EXTENSION_FORMAT, message = "유효한 이미지 확장자가 아닙니다.")
    private String imageUrl;
    @Min(value = 1, message = "가격은 0보다 작을 수 없습니다.")
    private BigDecimal price;

    public Product() {
    }

    public Product(Long id, String name, String imageUrl, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(final String name, final String imageUrl, final BigDecimal price) {
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

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }
}

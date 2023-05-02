package cart.dto.request;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class ProductRequest {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private final String name;
    private final int price;
    @NotBlank(message = "이미지 URL은 공백일 수 없습니다.")
    @URL(message = "유효한 url을 입력해 주세요.")
    private final String imageUrl;

    public ProductRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductRequest that = (ProductRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price)
                && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, imageUrl);
    }
}

package cart.domain.product;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class ProductImageUrl {
    private static final int MAX_LENGTH = 2083;

    @NotBlank
    @URL(message = "유효한 url을 입력해 주세요.")
    @Length(max = MAX_LENGTH, message = MAX_LENGTH + "자 이하의 url을 입력해 주세요.")
    private final String value;

    public ProductImageUrl(final String imageUrl) {
        this.value = imageUrl;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductImageUrl productImageUrl = (ProductImageUrl) o;
        return Objects.equals(value, productImageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

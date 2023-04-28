package cart.domain.product.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("NotBlank에서 null을 체크한다.")
    public void testNotBlank() {
        //given
        final ProductRequest request = new ProductRequest(null, 10, "imageUrl");

        //when
        final Optional<ConstraintViolation<ProductRequest>> result = validator.validate(request)
            .stream()
            .findFirst();

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getMessage()).isEqualTo("공백일 수 없습니다");
    }
}

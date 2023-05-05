package cart.entity;

import cart.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Nested
    @DisplayName("product 생성자 테스트")
    class initializeTest {

        private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        @ParameterizedTest
        @DisplayName("이름의 유효성 검증")
        @ValueSource(ints = {0, 256})
        void validateNameTest(int length) {
            String name = ".".repeat(length);

            Set<ConstraintViolation<Product>> violations = validator.validate(new Product(name, "www.google.co.kr.png", new BigDecimal(4000)));
            List<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages).contains("이름은 0자 초과 255미만이어야 합니다.");
        }

        @ParameterizedTest
        @DisplayName("가격의 유효성 검증")
        @ValueSource(ints = {-100})
        void validatePrice(int price) {
            Set<ConstraintViolation<Product>> violations = validator.validate(new Product("상품", "www.google.co.kr.png", new BigDecimal(price)));
            List<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages).contains("가격은 0보다 작을 수 없습니다.");
        }

        @ParameterizedTest
        @DisplayName("이미지 url 유효성 검증")
        @ValueSource(strings = {"www.google.co.kr.mp4", "www.google.co.kr.exe"})
        void validateImageUrl(String url) {
            Set<ConstraintViolation<Product>> violations = validator.validate(new Product("상품", url, new BigDecimal(4000)));
            List<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages).contains("유효한 이미지 확장자가 아닙니다.");
        }
    }
}

package cart.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductCreationRequestTest {

    private static final String dummyName = "dummy";
    private static final String dummyImage = "dummy";
    private static final Integer dummyPrice = 10_000;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void close() {
        validatorFactory.close();
    }

    @DisplayName("이름이 입력값으로 들어올 때")
    @Nested
    class RequestNameParameterTest {

        @DisplayName("공백이 아닌 문자열이 들어오면 통과한다")
        @Test
        void name_validInput_Success() {
            // given
            final String nameInput = "logan";
            final ProductCreationRequest request = makeRequest(nameInput);

            // when
            final Set<ConstraintViolation<ProductCreationRequest>> violations = validator.validate(request);

            // then
            assertTrue(violations.isEmpty());
        }


        @DisplayName("공백인 문자열이 들어오면 에러를 반환한다")
        @ValueSource(strings = {"", " "})
        @ParameterizedTest(name = "공백 문자열 {index}: {0}")
        void throwExceptionWhenNameHasBlank(final String nameInput) {
            // given
            final ProductCreationRequest request = makeRequest(nameInput);

            // when
            final Set<ConstraintViolation<ProductCreationRequest>> violations = makeViolation(request);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductCreationRequest makeRequest(final String nameInput) {
            return new ProductCreationRequest(nameInput, dummyImage, dummyPrice);
        }
    }

    @DisplayName("이미지 경로가 입력값으로 들어올 때")
    @Nested
    class RequestImageParameterTest {

        @DisplayName("공백이 아닌 문자열이 들어오면 통과한다")
        @Test
        void image_validInput_Success() {
            // given
            final String imageInput = "test";
            final ProductCreationRequest request = makeRequest(imageInput);

            // when
            final Set<ConstraintViolation<ProductCreationRequest>> violations = validator.validate(request);

            // then
            assertTrue(violations.isEmpty());
        }

        @DisplayName("공백인 문자열이 들어오면 에러를 반환한다")
        @ValueSource(strings = {"", " "})
        @ParameterizedTest(name = "공백 문자열 {index}: {0}")
        void throwExceptionWhenImageHasBlank(final String imageInput) {
            // given
            final ProductCreationRequest request = makeRequest(imageInput);

            // when
            final Set<ConstraintViolation<ProductCreationRequest>> violations = makeViolation(request);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductCreationRequest makeRequest(final String imageInput) {
            return new ProductCreationRequest(dummyName, imageInput, dummyPrice);
        }
    }

    @DisplayName("상품 금액이 입력값으로 들어올 때")
    @Nested
    class RequestPriceParameterTest {

        @DisplayName("공백이 아닌 문자열이 들어오면 통과한다")
        @Test
        void image_validInput_Success() {
            // given
            final Integer priceInput = 1;
            final ProductCreationRequest request = makeRequest(priceInput);

            // when
            final Set<ConstraintViolation<ProductCreationRequest>> violations = validator.validate(request);

            // then
            assertTrue(violations.isEmpty());
        }

        @DisplayName("상품 경계값을 넘는 가격을 가지면 에러를 반환한다")
        @ValueSource(ints = {0, 10_000_001})
        @ParameterizedTest(name = "경계값을 넘는 가격 {index}: {0}")
        void throwExceptionWhenImageHasBlank(final Integer priceInput) {
            // given
            final ProductCreationRequest request = makeRequest(priceInput);

            // when
            final Set<ConstraintViolation<ProductCreationRequest>> violations = makeViolation(request);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductCreationRequest makeRequest(final Integer priceInput) {
            return new ProductCreationRequest(dummyName, dummyImage, priceInput);
        }
    }

    private Set<ConstraintViolation<ProductCreationRequest>> makeViolation(final ProductCreationRequest request) {
        return validator.validate(request);
    }
}

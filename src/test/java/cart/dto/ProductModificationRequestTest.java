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

class ProductModificationRequestTest {

    private final Long dummyId = 1L;
    private final String dummyName = "dummy";
    private final String dummyImage = "dummy";
    private final Integer dummyPrice = 10_000;

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

    @DisplayName("ID가 입력값으로 들어올 때")
    @Nested
    class RequestIdParameterTest {

        @DisplayName("숫자가 들어오면 통과한다")
        @Test
        void id_validInput_Success() {
            // given
            final Long idInput = 1L;
            final ProductModificationRequest request = makeRequest(idInput);

            // when
            final Set<ConstraintViolation<ProductModificationRequest>> violations = validator.validate(request);

            // then
            assertTrue(violations.isEmpty());
        }

        @DisplayName("null이 들어오면 에러를 반환한다")
        @Test
        void throwExceptionWhenIdIsNull() {
            // given
            final Long idInput = null;
            final ProductModificationRequest request = makeRequest(idInput);

            // when
            final Set<ConstraintViolation<ProductModificationRequest>> violations = validator.validate(request);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductModificationRequest makeRequest(final Long idInput) {
            return new ProductModificationRequest(idInput, dummyName, dummyImage, dummyPrice);
        }
    }

    @DisplayName("이름이 입력값으로 들어올 때")
    @Nested
    class RequestNameParameterTest {

        @DisplayName("공백이 아닌 문자열이 들어오면 통과한다")
        @Test
        void name_validInput_Success() {
            // given
            final String nameInput = "logan";
            final ProductModificationRequest request = makeRequest(nameInput);

            // when
            final Set<ConstraintViolation<ProductModificationRequest>> violations = validator.validate(request);

            // then
            assertTrue(violations.isEmpty());
        }


        @DisplayName("공백인 문자열이 들어오면 에러를 반환한다")
        @ValueSource(strings = {"", " "})
        @ParameterizedTest(name = "공백 문자열 {index}: {0}")
        void throwExceptionWhenNameHasBlank(final String nameInput) {
            // given
            final ProductModificationRequest request = makeRequest(nameInput);

            // when
            final Set<ConstraintViolation<ProductModificationRequest>> violations = makeViolation(request);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductModificationRequest makeRequest(final String nameInput) {
            return new ProductModificationRequest(dummyId, nameInput, dummyImage, dummyPrice);
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
            final ProductModificationRequest request = makeRequest(imageInput);

            // when
            final Set<ConstraintViolation<ProductModificationRequest>> violations = validator.validate(request);

            // then
            assertTrue(violations.isEmpty());
        }

        @DisplayName("공백인 문자열이 들어오면 에러를 반환한다")
        @ValueSource(strings = {"", " "})
        @ParameterizedTest(name = "공백 문자열 {index}: {0}")
        void throwExceptionWhenImageHasBlank(final String imageInput) {
            // given
            final ProductModificationRequest request = makeRequest(imageInput);

            // when
            final Set<ConstraintViolation<ProductModificationRequest>> violations = makeViolation(request);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductModificationRequest makeRequest(final String imageInput) {
            return new ProductModificationRequest(dummyId, dummyName, imageInput, dummyPrice);
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
            final ProductModificationRequest request = makeRequest(priceInput);

            // when
            final Set<ConstraintViolation<ProductModificationRequest>> violations = validator.validate(request);

            // then
            assertTrue(violations.isEmpty());
        }

        @DisplayName("상품 경계값을 넘는 가격을 가지면 에러를 반환한다")
        @ValueSource(ints = {0, 10_000_001})
        @ParameterizedTest(name = "경계값을 넘는 가격 {index}: {0}")
        void throwExceptionWhenImageHasBlank(final Integer priceInput) {
            // given
            final ProductModificationRequest request = makeRequest(priceInput);

            // when
            final Set<ConstraintViolation<ProductModificationRequest>> violations = makeViolation(request);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductModificationRequest makeRequest(final Integer priceInput) {
            return new ProductModificationRequest(dummyId, dummyName, dummyImage, priceInput);
        }
    }

    private Set<ConstraintViolation<ProductModificationRequest>> makeViolation(final ProductModificationRequest request) {
        return validator.validate(request);
    }
}

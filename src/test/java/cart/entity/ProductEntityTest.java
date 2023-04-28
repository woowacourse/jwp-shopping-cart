package cart.entity;

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

class ProductEntityTest {

    private final Long dummyId = 1L;
    private final String dummyName = "dummy";
    private final String dummyImage = "http:";
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

    @DisplayName("이름이 입력값으로 들어올 때")
    @Nested
    class ProductNameParameterTest {

        @DisplayName("공백이 아닌 문자열이 들어오면 통과한다")
        @Test
        void name_validInput_Success() {
            // given
            final String nameInput = "logan";
            final ProductEntity product = makeProduct(nameInput);

            // when
            final Set<ConstraintViolation<ProductEntity>> violations = validator.validate(product);

            // then
            assertTrue(violations.isEmpty());
        }


        @DisplayName("공백인 문자열이 들어오면 에러를 반환한다")
        @ValueSource(strings = {"", " "})
        @ParameterizedTest(name = "공백 문자열 {index}: {0}")
        void throwExceptionWhenNameHasBlank(final String nameInput) {
            // given
            final ProductEntity product = makeProduct(nameInput);

            // when
            final Set<ConstraintViolation<ProductEntity>> violations = makeViolation(product);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductEntity makeProduct(final String nameInput) {
            return ProductEntity.of(dummyId, nameInput, dummyImage, dummyPrice);
        }
    }

    @DisplayName("이미지 경로가 입력값으로 들어올 때")
    @Nested
    class ProductImageParameterTest {

        @DisplayName("공백이 아닌 문자열이 들어오면 통과한다")
        @Test
        void image_validInput_Success() {
            // given
            final String imageInput = "http:";
            final ProductEntity product = makeProduct(imageInput);

            // when
            final Set<ConstraintViolation<ProductEntity>> violations = validator.validate(product);

            // then
            assertTrue(violations.isEmpty());
        }

        @DisplayName("null이 들어오면 에러를 반환한다")
        @Test
        void throwExceptionWhenImageIsNull() {
            // given
            final String imageInput = null;
            final ProductEntity product = makeProduct(imageInput);

            // when
            final Set<ConstraintViolation<ProductEntity>> violations = makeViolation(product);

            // then
            assertFalse(violations.isEmpty());
        }

        @DisplayName("URL 형식이 아닌 경로가 들어오면 에러를 반환한다")
        @ValueSource(strings = {" ", "test", "test:."})
        @ParameterizedTest(name = "URL 형식이 아닌 문자열 {index}: {0}")
        void throwExceptionWhenImageHasBlank(final String imageInput) {
            // given
            final ProductEntity product = makeProduct(imageInput);

            // when
            final Set<ConstraintViolation<ProductEntity>> violations = makeViolation(product);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductEntity makeProduct(final String imageInput) {
            return ProductEntity.of(dummyId, dummyName, imageInput, dummyPrice);
        }
    }

    @DisplayName("상품 금액이 입력값으로 들어올 때")
    @Nested
    class ProductPriceParameterTest {

        @DisplayName("공백이 아닌 문자열이 들어오면 통과한다")
        @Test
        void image_validInput_Success() {
            // given
            final Integer priceInput = 1;
            final ProductEntity product = makeProduct(priceInput);

            // when
            final Set<ConstraintViolation<ProductEntity>> violations = validator.validate(product);

            // then
            assertTrue(violations.isEmpty());
        }

        @DisplayName("상품 경계값을 넘는 가격을 가지면 에러를 반환한다")
        @ValueSource(ints = {0, 10_000_001})
        @ParameterizedTest(name = "경계값을 넘는 가격 {index}: {0}")
        void throwExceptionWhenImageHasBlank(final Integer priceInput) {
            // given
            final ProductEntity product = makeProduct(priceInput);

            // when
            final Set<ConstraintViolation<ProductEntity>> violations = makeViolation(product);

            // then
            assertFalse(violations.isEmpty());
        }

        private ProductEntity makeProduct(final Integer priceInput) {
            return ProductEntity.of(dummyId, dummyName, dummyImage, priceInput);
        }
    }

    private Set<ConstraintViolation<ProductEntity>> makeViolation(final ProductEntity product) {
        return validator.validate(product);
    }
}

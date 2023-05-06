package cart.dto;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static cart.DummyData.DUMMY_PRODUCT_ONE;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductCreationRequestTest {

    private final String dummyName = DUMMY_PRODUCT_ONE.getName();
    private final String dummyImageUrl = DUMMY_PRODUCT_ONE.getImageUrl();
    private final Integer dummyPrice = DUMMY_PRODUCT_ONE.getPrice();
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void 제대로_된_데이터_형식이_들어오면_성공한다() {
        final ProductCreationRequest request = new ProductCreationRequest(dummyName, dummyImageUrl, dummyPrice);

        final Set<ConstraintViolation<ProductCreationRequest>> constraintViolations = validator.validate(request);

        assertThat(constraintViolations.isEmpty()).isTrue();
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 이름이_없으면_에러를_반환한다(final String input) {
        final ProductCreationRequest request = new ProductCreationRequest(input, dummyImageUrl, dummyPrice);

        final Set<ConstraintViolation<ProductCreationRequest>> constraintViolations = validator.validate(request);

        assertThat(constraintViolations.isEmpty()).isFalse();
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 이미지_경로가_없으면_에러를_반환한다(final String input) {
        final ProductCreationRequest request = new ProductCreationRequest(dummyName, input, dummyPrice);

        final Set<ConstraintViolation<ProductCreationRequest>> constraintViolations = validator.validate(request);

        assertThat(constraintViolations.isEmpty()).isFalse();
    }

    @NullSource
    @ParameterizedTest
    void 가격이_없으면_에러를_반환한다(final Integer input) {
        final ProductCreationRequest request = new ProductCreationRequest(dummyName, dummyImageUrl, input);

        final Set<ConstraintViolation<ProductCreationRequest>> constraintViolations = validator.validate(request);

        assertThat(constraintViolations.isEmpty()).isFalse();
    }
}

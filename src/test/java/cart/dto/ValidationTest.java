package cart.dto;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static cart.DummyData.DUMMY_PRODUCT_ONE;

public abstract class ValidationTest {

    protected final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    protected final Validator validator = factory.getValidator();

    protected final Long dummyId = DUMMY_PRODUCT_ONE.getId();
    protected final String dummyName = DUMMY_PRODUCT_ONE.getName();
    protected final String dummyImageUrl = DUMMY_PRODUCT_ONE.getImageUrl();
    protected final Integer dummyPrice = DUMMY_PRODUCT_ONE.getPrice();
}

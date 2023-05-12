package cart.controller.product.dto;

import cart.common.auth.AuthHeaderExtractor;
import cart.controller.product.ProductRestController;
import cart.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = ProductRestController.class)
class ProductRequestTest {

    @Autowired
    Validator validator;

    @MockBean
    AuthHeaderExtractor authHeaderExtractor;

    @MockBean
    ProductService productService;

    @Test
    void 이미지값은_notNull() {
        ProductCreateRequest request = new ProductCreateRequest(null, "name", 1000);
        Set<ConstraintViolation<ProductCreateRequest>> validate = validator.validate(request);

        Iterator<ConstraintViolation<ProductCreateRequest>> iterator = validate.iterator();

        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<ProductCreateRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages).contains("이미지 url은 비어있을 수 없습니다.");
    }

    @Test
    void 상품_이름은_notNull() {
        ProductCreateRequest request = new ProductCreateRequest("image", null, 1000);
        Set<ConstraintViolation<ProductCreateRequest>> validate = validator.validate(request);

        Iterator<ConstraintViolation<ProductCreateRequest>> iterator = validate.iterator();

        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<ProductCreateRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages).contains("상품 이름은 비어있을 수 없습니다.");
    }

    @Test
    void 상품_이름은_notBlank() {
        ProductCreateRequest request = new ProductCreateRequest("image", "", 1000);
        Set<ConstraintViolation<ProductCreateRequest>> validate = validator.validate(request);

        Iterator<ConstraintViolation<ProductCreateRequest>> iterator = validate.iterator();

        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<ProductCreateRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages).contains("상품 이름은 최소 한 글자 입니다.");
    }

    @Test
    void 상품_가격은_notNull() {
        ProductCreateRequest request = new ProductCreateRequest("image", "name", null);
        Set<ConstraintViolation<ProductCreateRequest>> validate = validator.validate(request);

        Iterator<ConstraintViolation<ProductCreateRequest>> iterator = validate.iterator();

        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<ProductCreateRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages).contains("가격은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void 상품_가격은_양수(int price) {
        ProductCreateRequest request = new ProductCreateRequest("image", "name", price);
        Set<ConstraintViolation<ProductCreateRequest>> validate = validator.validate(request);

        Iterator<ConstraintViolation<ProductCreateRequest>> iterator = validate.iterator();

        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<ProductCreateRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages).contains("가격은 0 이상이어야 합니다.");
    }

}

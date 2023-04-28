package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.ProductRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
class AdminControllerTest extends ProductSteps {

    @Test
    void 상품을_저장하는_테스트한다() {
        // when
        final ExtractableResponse<Response> response = 치킨을_저장한다();
        
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).contains("/admin/product/")
        );
    }

    @Test
    void 상품_수정_테스트를_진행한다() {
        // given
        치킨을_저장한다();
        final ProductRequest request = new ProductRequest("샐러드", 20000, "changedImg");

        // when
        final ExtractableResponse<Response> response = 상품을_수정한다(request, 1L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 상품_삭제_테스트를_진행한다() {
        // when
        final ExtractableResponse<Response> response = 상품을_삭제한다(1L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}

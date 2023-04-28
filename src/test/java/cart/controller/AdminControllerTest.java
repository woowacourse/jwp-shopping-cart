package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.ProductRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
class AdminControllerTest extends ProductSteps {

    @Nested
    class 상품을_저장하는_테스트를_진행한다 {

        @Test
        void 상품_저장에_성공한다() {
            // when
            final ExtractableResponse<Response> response = 치킨을_저장한다();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                    () -> assertThat(response.header("Location")).contains("/admin/product/")
            );
        }

        @Test
        void 이름의_길이가_20자_초과이면_오류가_발생한다() {
            // when
            final ExtractableResponse<Response> response = 상품을_저장한다("012345678901234567891", 10000, "imgUrl");

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(getErrorMessage(response)).contains("1글자 이상 20글자 이하로만 입력가능 합니다.")
            );
        }

        @Test
        void 이름의_없으면_오류가_발생한다() {
            // when
            final ExtractableResponse<Response> response = 상품을_저장한다("", 10000, "imgUrl");

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(getErrorMessage(response)).contains("상품의 이름이 입력되지 않았습니다.")
            );
        }

        @Test
        void 가격의_범위가_맞지_않으면_오류가_발생한다() {
            // when
            final ExtractableResponse<Response> saveProductUnder10 = 상품을_저장한다("샐러드", 9, "imgUrl");
            final ExtractableResponse<Response> saveProductOver1000000 = 상품을_저장한다("샐러드", 10000001, "imgUrl");

            // then
            assertAll(
                    () -> assertThat(saveProductUnder10.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(getErrorMessage(saveProductUnder10))
                            .contains("상품 금액은 10원 이상 1000000이하의 정수만 입력가능 합니다."),
                    () -> assertThat(saveProductOver1000000.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(getErrorMessage(saveProductOver1000000))
                            .contains("상품 금액은 10원 이상 1000000이하의 정수만 입력가능 합니다.")
            );
        }

        @Test
        void 이미지가_없으면_저장에_실패한다() {
            // when
            final ExtractableResponse<Response> response = 상품을_저장한다("치킨", 10000, "");

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(getErrorMessage(response))
                            .contains("상품의 사진을 등록해주세요.")
            );
        }
    }

    private String getErrorMessage(final ExtractableResponse<Response> restAssuredResponse) {
        return restAssuredResponse.jsonPath().getString("message");
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

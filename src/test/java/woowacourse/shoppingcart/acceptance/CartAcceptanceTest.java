package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.cart.CartRequest;
import woowacourse.shoppingcart.dto.cart.RemovedCartsRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.로그인;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원가입;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @DisplayName("장바구니 상품 추가")
    @Test
    void save() {
        // given
        // 로그인하여 토큰이 발급 되어 있고
        회원가입(new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS));
        String token = 로그인(new TokenRequest(EMAIL, PASSWORD));

        // when
        // 장바구니에 물품을 추가하면
        ExtractableResponse<Response> extract = 장바구니_상품_추가(token, new CartRequest(1, 3));

        // then
        // 정상적으로 상품이 추가된다.
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("장바구니 상품 삭제")
    @Test
    void remove() {
        // given
        // 로그인하여 토큰 발급과 장바구니에 물품이 추가되어 있고
        회원가입(new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS));
        String token = 로그인(new TokenRequest(EMAIL, PASSWORD));
        장바구니_상품_추가(token, new CartRequest(1, 3));
        장바구니_상품_추가(token, new CartRequest(2, 3));

        // when
        // 장바구니에 있는 물품을 삭제하면
        RemovedCartsRequest request = new RemovedCartsRequest(List.of(1, 2));
        ExtractableResponse<Response> extract = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/customers/carts")
                .then().log().all().extract();

        // then
        // 정상적으로 삭제된다.
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 상품 수정")
    @Test
    void edit() {
        // given
        // 로그인하여 토큰 발급과 장바구니에 물품이 추가되어 있고
        회원가입(new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS));
        String token = 로그인(new TokenRequest(EMAIL, PASSWORD));
        장바구니_상품_추가(token, new CartRequest(1, 3));
        장바구니_상품_추가(token, new CartRequest(2, 3));

        // when
        // 장바구니에 있는 물품을 수정하면
        CartRequest request = new CartRequest(1, 5);
        ExtractableResponse<Response> extract = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/customers/carts")
                .then().log().all().extract();

        // then
        // 정상적으로 수정된다.
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("장바구니 상품 조회")
    @Test
    void findAll() {
        // given
        // 로그인하여 토큰 발급과 장바구니에 물품이 추가되어 있고
        회원가입(new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS));
        String token = 로그인(new TokenRequest(EMAIL, PASSWORD));
        장바구니_상품_추가(token, new CartRequest(1, 3));
        장바구니_상품_추가(token, new CartRequest(2, 3));

        // when
        // 장바구니에 있는 물품을 조회하면
        ExtractableResponse<Response> extract = 장바구니_상품_목록_조회_요청(token);

        // then
        // 정상적으로 조회된다.
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }



    private ExtractableResponse<Response> 장바구니_상품_추가(String token, CartRequest request) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/carts")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> 장바구니_상품_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/carts")
                .then().log().all().extract();
    }
}

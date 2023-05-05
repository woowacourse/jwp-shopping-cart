package cart.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.dto.cartitem.CartItemRequest;
import cart.dto.member.MemberRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("사용자가 상품을 장바구니에 담는 것을 성공한다.")
    void member_put_product_success() {
        // given
        MemberRequest member = new MemberRequest("ako@wooteco.com","ako");
        CartItemRequest cartItemRequest = new CartItemRequest(1L);
        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(member.getEmail(), member.getPassword())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(member).body(cartItemRequest)
            .when()
            .post("/cart-items")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("createWrongMemberORProduct")
    @DisplayName("사용자가 상품을 담는 것을 실패한다.")
    void member_put_product_fail(MemberRequest memberRequest, CartItemRequest cartItemRequest) {
        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(memberRequest.getEmail(), memberRequest.getPassword())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberRequest).body(cartItemRequest)
            .when()
            .post("/cart-items")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private static Stream<Arguments> createWrongMemberORProduct() {
        return Stream.of(
            Arguments.arguments(new MemberRequest("fail@wooteco.com","fail"), new CartItemRequest(1L)),
            Arguments.arguments(new MemberRequest("ako@wooteco.com","ako"), new CartItemRequest(100L)),
            Arguments.arguments(new MemberRequest("fail@wooteco.com","fail"), new CartItemRequest(100L))
        );
    }

    @Test
    @DisplayName("사용자가 자신의 장바구니 상품을 조회한다.")
    void member_find_cart_item_success() {
        // given
        MemberRequest member = new MemberRequest("ako@wooteco.com","ako");

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(member.getEmail(), member.getPassword())
            .when()
            .get("/cart-items")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.response().as(List.class).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("잘못된 사용자가 자신의 장바구니 상품을 조회한다.")
    void member_find_cart_item_fail() {
        // given
        MemberRequest member = new MemberRequest("fail@wooteco.com","fail");

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(member.getEmail(), member.getPassword())
            .when()
            .get("/cart-items")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("사용자가 장바구니 상품을 삭제한다.")
    void member_delete_cart_item_success() {
        // given
        final MemberRequest member = new MemberRequest("ako@wooteco.com","ako");
        final CartItemRequest cartItemRequest = new CartItemRequest(1L);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(member.getEmail(), member.getPassword())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete("/cart-items/" + cartItemRequest.getProductId())
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("deleteWrongMemberORProduct")
    @DisplayName("사용자가 장바구니 상품을 삭제하는 것을 실패한다.")
    void member_delete_cart_item_fail(final MemberRequest memberRequest, final CartItemRequest cartItemRequest) {
        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(memberRequest.getEmail(), memberRequest.getPassword())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete("/cart-items/" + cartItemRequest.getProductId())
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private static Stream<Arguments> deleteWrongMemberORProduct() {
        return Stream.of(
            Arguments.arguments(new MemberRequest("fail@wooteco.com","fail"), new CartItemRequest(1L)),
            Arguments.arguments(new MemberRequest("ako@wooteco.com","ako"), new CartItemRequest(100L)),
            Arguments.arguments(new MemberRequest("fail@wooteco.com","fail"), new CartItemRequest(100L))
        );
    }
}

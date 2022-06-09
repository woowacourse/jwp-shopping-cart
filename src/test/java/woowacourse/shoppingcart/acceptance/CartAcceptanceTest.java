package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartsResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    private Long notFoundProductId;

    @Override
    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp(restDocumentation);

        productId1 = 상품_추가("치약", 1600, "image 치약").jsonPath().getLong("id");
        productId2 = 상품_추가("칫솔", 4300, "image 칫솔").jsonPath().getLong("id");
        notFoundProductId = Math.max(productId1, productId2) + 1L;
    }

    @DisplayName("잘못된 토큰으로 장바구니 상품 추가 시 401 반환")
    @Test
    void addProductWithInvalidToken() {
        ExtractableResponse<Response> response = 장바구니_추가_요청("Invalid Token", productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("존재하지 않는 상품 장바구니 추가 시 404 반환")
    @Test
    void addNotFoundProduct() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> response = 장바구니_추가_요청(token, notFoundProductId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartProduct() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header(org.apache.http.HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(Map.of("productId", productId1))
            .filter(document("create-cart-item",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("컨텐츠 타입"),
                    headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 토큰")
                ),
                requestFields(
                    fieldWithPath("productId").description("장바구니에 담을 상품 식별 번호")
                )
            ))
            .when().log().all()
            .post("/users/me/carts")
            .then().log().all()
            .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("중복된 장바구니 아이템 추가")
    @Test
    void addDuplicateCartProduct() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");
        장바구니_추가_요청(token, productId1);

        ExtractableResponse<Response> response = 장바구니_추가_요청(token, productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getInt("errorCode"))
            .isEqualTo(DUPLICATE_CART_ITEM_ERROR_CODE);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");
        장바구니_추가_요청(token, productId1);
        장바구니_추가_요청(token, productId2);

        ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .when().log().all()
            .filter(document("query-cart-item",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 토큰")
                ),
                responseFields(
                    fieldWithPath("cartList[]").description("장바구니에 담은 상품들"),
                    fieldWithPath("cartList[].id").description("장바구니에 담은 상품 식별 번호"),
                    fieldWithPath("cartList[].name").description("장바구니에 담은 상품 이름"),
                    fieldWithPath("cartList[].imageUrl").description("장바구니에 담은 상품 이미지 URL"),
                    fieldWithPath("cartList[].price").description("장바구니에 담은 상품 가격"),
                    fieldWithPath("cartList[].quantity").description("장바구니에 담은 상품 갯수")
                )
            ))
            .get("/users/me/carts")
            .then().log().all()
            .extract();

        CartsResponse cartsResponse = response.jsonPath()
            .getObject(".", CartsResponse.class);

        assertThat(cartsResponse.getCartList())
            .extracting(CartResponse::getId, CartResponse::getName, CartResponse::getPrice,
                CartResponse::getImageUrl, CartResponse::getQuantity)
            .containsExactlyInAnyOrder(
                tuple(productId1, "치약", 1600, "image 치약", 1),
                tuple(productId2, "칫솔", 4300, "image 칫솔", 1));
    }

    @DisplayName("잘못된 토큰으로 장바구니 아이템 목록 조회 시 401 반환")
    @Test
    void getCartItemsWithInvalidToken() {
        ExtractableResponse<Response> response = 장바구니_목록_조회("invalid token");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");
        장바구니_추가_요청(token, productId1);

        ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .when().log().all()
            .filter(document("delete-cart-item",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 토큰")
                ),
                pathParameters(
                    parameterWithName("productId").description("상품 식별 번호")
                )
            ))
            .delete("/users/me/carts/{productId}", productId1)
            .then().log().all()
            .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        CartsResponse cartsResponse = 장바구니_목록_조회(token).jsonPath()
            .getObject(".", CartsResponse.class);
        assertThat(cartsResponse.getCartList()).isEmpty();
    }

    @DisplayName("잘못된 토큰으로 장바구니 삭제")
    @Test
    void deleteCartItemByInvalidToken() {
        ExtractableResponse<Response> response = 장바구니_삭제("invalid token", productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("장바구니에 없는 상품 삭제")
    @Test
    void deleteNotExistCartItem() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> response = 장바구니_삭제(token, productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(
            NOT_EXIST_CART_ITEM_ERROR_CODE);
    }

    @DisplayName("존재하지 않는 상품 장바구니에서 삭제")
    @Test
    void deleteNotFoundCartItem() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> response = 장바구니_삭제(token, notFoundProductId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("장바구니 수정")
    @Test
    void updateCartItem() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");
        장바구니_추가_요청(token, productId1);

        ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(Map.of("quantity", "4"))
            .when().log().all()
            .filter(document("update-cart-item",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 토큰"),
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("컨텐츠 타입")
                ),
                pathParameters(
                    parameterWithName("productId").description("상품 식별 번호")
                ),
                requestFields(
                    fieldWithPath("quantity").description("상품 수량")
                ),
                responseFields(
                    fieldWithPath("id").description("장바구니에 담은 상품 식별 번호"),
                    fieldWithPath("name").description("장바구니에 담은 상품 이름"),
                    fieldWithPath("imageUrl").description("장바구니에 담은 상품 이미지 URL"),
                    fieldWithPath("price").description("장바구니에 담은 상품 가격"),
                    fieldWithPath("quantity").description("장바구니에 담은 상품 갯수")
                )
            ))
            .put("/users/me/carts/{productId}", productId1)
            .then().log().all()
            .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        CartsResponse cartsResponse = 장바구니_목록_조회(token).jsonPath()
            .getObject(".", CartsResponse.class);

        assertThat(cartsResponse.getCartList())
            .extracting(CartResponse::getId, CartResponse::getName, CartResponse::getPrice,
                CartResponse::getImageUrl, CartResponse::getQuantity)
            .containsExactlyInAnyOrder(
                tuple(productId1, "치약", 1600, "image 치약", 4));
    }

    @DisplayName("잘못된 토큰으로 장바구니 수정")
    @Test
    void updateCartItemByInvalidToken() {
        ExtractableResponse<Response> response = 장바구니_수정("4", "invalid token", productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("잘못된 형식의 수량으로 장바구니 수정")
    @Test
    void updateCartItemByInvalidQuantity() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");
        장바구니_추가_요청(token, productId1);

        ExtractableResponse<Response> response = 장바구니_수정("0", token, productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("존재하지 않는 상품 장바구니 수정")
    @Test
    void updateNotFoundCartItem() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> response = 장바구니_수정("2", token, notFoundProductId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("담지 않는 상품 장바구니 수정")
    @Test
    void updateNotExistCartItem() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> response = 장바구니_수정("2", token, productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getInt("errorCode"))
            .isEqualTo(NOT_EXIST_CART_ITEM_ERROR_CODE);
    }
}

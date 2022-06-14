package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    private final static String[] NAMES = {"치약", "칫솔", "비누"};
    private final static Integer[] PRICES = {1600, 2200, 4300};
    private final static String[] IMAGE_URLS = {"image 치약", "image 칫솔", "image 비누"};
    private long[] productIds;

    @Override
    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp(restDocumentation);

        productIds = new long[3];
        for (int i = 0; i < 3; i++) {
            productIds[i] = 상품_추가(NAMES[i], PRICES[i], IMAGE_URLS[i]).jsonPath().getLong("id");
        }
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        RestAssured
            .given(spec).log().all()
            .filter(document("query-products",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("productList[]").description("상품 목록"),
                    fieldWithPath("productList[].id").description("상품 식별 번호"),
                    fieldWithPath("productList[].name").description("상품 이름"),
                    fieldWithPath("productList[].price").description("상품 가격"),
                    fieldWithPath("productList[].imageUrl").description("상품 이미지 URL")
                )
            ))
            .when().log().all()
            .get("/products")
            .then().log().all()
            .assertThat().statusCode(HttpStatus.OK.value())
            .assertThat().body("productList.name", hasItems(NAMES))
            .assertThat().body("productList.price", hasItems(PRICES))
            .assertThat().body("productList.imageUrl", hasItems(IMAGE_URLS));
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        RestAssured
            .given(spec).log().all()
            .filter(document("query-product",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("productId").description("상품 식별 번호")
                ),
                responseFields(
                    fieldWithPath("id").description("상품 식별 번호"),
                    fieldWithPath("name").description("상품 이름"),
                    fieldWithPath("price").description("상품 가격"),
                    fieldWithPath("imageUrl").description("상품 이미지 URL")
                )
            ))
            .when().log().all()
            .get("/products/{productId}", productIds[0])
            .then().log().all()
            .assertThat().statusCode(HttpStatus.OK.value())
            .assertThat().body("name", is(NAMES[0]))
            .assertThat().body("price", is(PRICES[0]))
            .assertThat().body("imageUrl", is(IMAGE_URLS[0]));
    }

    @DisplayName("존재하지 않는 상품 조회")
    @Test
    void getNotFoundProduct() {
        ExtractableResponse<Response> response = 상품_조회(100L);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("권한이 없는 일반 사용자가 상품 추가시 403 반환")
    @Test
    void addProductWithNormalUser() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");
        Map<String, Object> body = Map.of("name", "맥주", "price", 1000, "imageUrl", "image url");

        RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(body)
            .when().log().all()
            .post("/products")
            .then().log().all()
            .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("Admin 계정으로 상품 추가")
    @Test
    void addProductWithAdmin() {
        String adminToken = 토큰_요청(adminEmail, adminPassword);

        String name = "맥주";
        int price = 1000;
        String imageUrl = "image url";
        Map<String, Object> body = Map.of("name", name, "price", price, "imageUrl", imageUrl);

        long productId = RestAssured
            .given(spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
            .body(body)
            .filter(document("create-product",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("컨텐츠 타입"),
                    headerWithName(HttpHeaders.AUTHORIZATION).description("Admin 계정 Bearer 토큰")
                ),
                requestFields(
                    fieldWithPath("name").description("상품 이름"),
                    fieldWithPath("price").description("상품 가격"),
                    fieldWithPath("imageUrl").description("상품 이미지 URL")
                ),
                responseFields(
                    fieldWithPath("id").description("상품 식별 번호"),
                    fieldWithPath("name").description("상품 이름"),
                    fieldWithPath("price").description("상품 가격"),
                    fieldWithPath("imageUrl").description("상품 이미지 URL")
                )
            ))
            .when().log().all()
            .post("/products")
            .then().log().all()
            .assertThat().statusCode(HttpStatus.CREATED.value())
            .extract().jsonPath().getLong("id");

        ExtractableResponse<Response> 상품_생성_후_조회 = 상품_조회(productId);
        assertThat(상품_생성_후_조회.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(상품_생성_후_조회.jsonPath().getString("name")).isEqualTo(name);
        assertThat(상품_생성_후_조회.jsonPath().getInt("price")).isEqualTo(price);
        assertThat(상품_생성_후_조회.jsonPath().getString("imageUrl")).isEqualTo(imageUrl);
    }

    @DisplayName("잘못된 형식으로 상품 추가 시 400 반환")
    @ParameterizedTest
    @MethodSource("provideInvalidFormatProduct")
    void addProductWithInvalidFormat(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_추가(name, price, imageUrl);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(INVALID_FORMAT_ERROR_CODE);
    }

    private static Stream<Arguments> provideInvalidFormatProduct() {
        return Stream.of(
            Arguments.of("맥주", -1, "image url"),
            Arguments.of("술" .repeat(256), 1000, "image url"),
            Arguments.of("맥주", 1000, "i" .repeat(256))
        );
    }
}

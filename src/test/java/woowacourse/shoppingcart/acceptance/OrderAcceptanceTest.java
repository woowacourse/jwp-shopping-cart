package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@DisplayName("주문 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class OrderAcceptanceTest extends AcceptanceTest {
//    private static final String USER = "yeonlog";
//    private Long cartId1;
//    private Long cartId2;
//
//    @Override
//    @BeforeEach
//    public void setUp() {
//        super.setUp();
//
//        Long productId1 = 상품_등록("치킨", 10_000, "http://example.com/chicken.jpg");
//        Long productId2 = 상품_등록("맥주", 20_000, "http://example.com/beer.jpg");
//
//        String token = 회원_가입_후_토큰_발급("yeonlog", "abAB12!!");
//        cartId1 = 장바구니_추가(token, productId1);
//        cartId2 = 장바구니_추가(token, productId2);
//    }
//
//
//    private String 회원_가입_후_토큰_발급(String account, String password) {
//        Map<String, Object> createRequest = new HashMap<>();
//        createRequest.put("account", account);
//        createRequest.put("nickname", "연로그");
//        createRequest.put("password", password);
//        createRequest.put("address", "연로그네");
//
//        Map<String, String> phoneNumber = new HashMap<>();
//        phoneNumber.put("start", "010");
//        phoneNumber.put("middle", "1111");
//        phoneNumber.put("last", "1111");
//        createRequest.put("phoneNumber", phoneNumber);
//
//        int statusCode = RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(createRequest)
//                .when()
//                .post("/signup")
//                .then().log().all()
//                .extract()
//                .statusCode();
//        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
//
//        Map<String, Object> tokenRequest = new HashMap<>();
//        tokenRequest.put("account", account);
//        tokenRequest.put("password", password);
//
//        ExtractableResponse<Response> response = RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(tokenRequest)
//                .when()
//                .post("/signin")
//                .then().log().all()
//                .extract();
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//
//        return findValue(response, "accessToken");
//    }
//
//    private Long 장바구니_추가(String token, Long productId) {
//        Map<String, Object> request = new HashMap<>();
//        request.put("productId", productId);
//
//        ExtractableResponse<Response> response = RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header(AUTHORIZATION, BEARER + token)
//                .body(request)
//                .when().post("/customers/cart")
//                .then().log().all()
//                .extract();
//
//        return Long.parseLong(response.header(LOCATION).split("/cart/")[1]);
//    }
//
//    @DisplayName("주문하기")
//    @Test
//    void addOrder() {
//        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
//                .map(cartId -> new OrderRequest(cartId, 10))
//                .collect(Collectors.toList());
//
//        ExtractableResponse<Response> response = 주문하기_요청(USER, orderRequests);
//
//        주문하기_성공함(response);
//    }
//
//    @DisplayName("주문 내역 조회")
//    @Test
//    void getOrders() {
//        Long orderId1 = 주문하기_요청_성공되어_있음(USER, Collections.singletonList(new OrderRequest(cartId1, 2)));
//        Long orderId2 = 주문하기_요청_성공되어_있음(USER, Collections.singletonList(new OrderRequest(cartId2, 5)));
//
//        ExtractableResponse<Response> response = 주문_내역_조회_요청(USER);
//
//        주문_조회_응답됨(response);
//        주문_내역_포함됨(response, orderId1, orderId2);
//    }
//
//    @DisplayName("주문 단일 조회")
//    @Test
//    void getOrder() {
//        Long orderId = 주문하기_요청_성공되어_있음(USER, Arrays.asList(
//                new OrderRequest(cartId1, 2),
//                new OrderRequest(cartId2, 4)
//        ));
//
//        ExtractableResponse<Response> response = 주문_단일_조회_요청(USER, orderId);
//
//        주문_조회_응답됨(response);
//        주문_조회됨(response, orderId);
//    }
//
//    public static ExtractableResponse<Response> 주문하기_요청(String userName, List<OrderRequest> orderRequests) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(orderRequests)
//                .when().post("/api/customers/{customerName}/orders", userName)
//                .then().log().all()
//                .extract();
//    }
//
//    public static ExtractableResponse<Response> 주문_내역_조회_요청(String userName) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/api/customers/{customerName}/orders", userName)
//                .then().log().all()
//                .extract();
//    }
//
//    public static ExtractableResponse<Response> 주문_단일_조회_요청(String userName, Long orderId) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/api/customers/{customerName}/orders/{orderId}", userName, orderId)
//                .then().log().all()
//                .extract();
//    }
//
//    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.header("Location")).isNotBlank();
//    }
//
//    public static Long 주문하기_요청_성공되어_있음(String userName, List<OrderRequest> orderRequests) {
//        ExtractableResponse<Response> response = 주문하기_요청(userName, orderRequests);
//        return Long.parseLong(response.header("Location").split("/orders/")[1]);
//    }
//
//    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
//        List<Long> resultOrderIds = response.jsonPath().getList(".", OrderResponse.class).stream()
//                .map(OrderResponse::getOrderId)
//                .collect(Collectors.toList());
//        assertThat(resultOrderIds).contains(orderIds);
//    }
//
//    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
//        OrderResponse resultOrder = response.as(OrderResponse.class);
//        assertThat(resultOrder.getOrderId()).isEqualTo(orderId);
//    }
//
//    private Long 상품_등록(String name, int price, String imageUrl) {
//        ExtractableResponse<Response> response = RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(상품_정보(name, price, imageUrl))
//                .when().post("/products")
//                .then().log().all()
//                .extract();
//
//        return Long.parseLong(response.header(LOCATION).split("/products/")[1]);
//    }
//
//    private Map<String, Object> 상품_정보(String name, int price, String imageUrl) {
//        Map<String, Object> request = new HashMap<>();
//        request.put("name", name);
//        request.put("price", price);
//        request.put("imageUrl", imageUrl);
//        return request;
//    }
}

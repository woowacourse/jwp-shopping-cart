package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.config.RestDocsConfig;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static woowacourse.fixture.TokenFixture.ACCESS_TOKEN;
import static woowacourse.fixture.TokenFixture.BEARER;

@DisplayName("상품 API 문서화")
@AutoConfigureRestDocs
@WebMvcTest(ProductController.class)
@Import(RestDocsConfig.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @MockBean
    private AuthService authService;


    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("로그인한 사용자의 상품 전체 조회할 때 redirect 문서화")
    @Test
    void productsWithRedirect() throws Exception {
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getPayload(any())).willReturn("1");

        ResultActions results = mvc.perform(get("/api/products")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(redirectedUrl("/api/products/me"))
                .andDo(print())
                .andDo(document("products-get-redirect",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("유저가 로그인 상태면 토큰이 들어있고 /api/products/me로 리다이렉트 되고, " +
                                        "로그인이 안된 상태면 토큰이 없습니다.").optional()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("redirect할 url입니다.")
                        )
                ));
    }

    @DisplayName("로그인한 사용자의 상품 전체 조회 문서화")
    @Test
    void productsWithLogin() throws Exception {
        Customer customer = new Customer(1L, "giron", new Password("!@3123ASD"));
        List<Product> products = List.of(
                new Product(1L, "제품1", 5000, "www.imageUrl1.com"),
                new Product(2L, "제품2", 1000, "www.image2.com"));

        List<ProductResponse> responses = products.stream()
                .map(ProductResponse::new).collect(Collectors.toList());

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(authService.getAuthenticatedCustomer(ACCESS_TOKEN)).willReturn(customer);
        given(productService.findProductsByCustomer(customer)).willReturn(responses);

        ResultActions results = mvc.perform(get("/api/products/me")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("products-get-login",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("상품의 식별자"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("상품의 이름"),
                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("상품 이미지 url"),
                                fieldWithPath("[].cartId").type(JsonFieldType.NUMBER).description("유저가 로그인했으면 장바구니 식별자, 안했으면 null").optional(),
                                fieldWithPath("[].quantity").type(JsonFieldType.NUMBER).description("유저가 로그인했으면 장바구니에 담은 수량, 안했으면 0")
                        )
                ));
    }

    @DisplayName("로그인 안 한 사용자의 상품 전체 조회 문서화")
    @Test
    void productsWithLogOut() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "제품1", 5000, "www.imageUrl1.com"),
                new Product(2L, "제품2", 1000, "www.image2.com"));

        List<ProductResponse> responses = products.stream()
                .map(ProductResponse::new).collect(Collectors.toList());

        given(productService.findAll()).willReturn(responses);

        ResultActions results = mvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("products-get-logout",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("상품의 식별자"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("상품의 이름"),
                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("상품 이미지 url"),
                                fieldWithPath("[].cartId").type(JsonFieldType.NUMBER).description("유저가 로그인했으면 장바구니 식별자, 안했으면 null").optional(),
                                fieldWithPath("[].quantity").type(JsonFieldType.NUMBER).description("유저가 로그인했으면 장바구니에 담은 수량, 안했으면 0").optional()
                        )
                ));
    }

    @DisplayName("상품 추가 문서화")
    @Test
    void add() throws Exception {

        ProductRequest.AllProperties request = new ProductRequest.AllProperties(1L, "새로운 상품 이름", 50000, "www.imageUrl.com");
        given(productService.addProduct(any())).willReturn(1L);

        ResultActions results = mvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(request)));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("product-add",
                        responseHeaders(
                                headerWithName("Location").description("Location의 url마지막에 product의 식별자가 있습니다")
                        ),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상품의 식별자"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("상품의 이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("상품 이미지 url")
                        )
                ));
    }

    @DisplayName("상품 단건 조회 문서화")
    @Test
    void product() throws Exception {
        ProductResponse response = new ProductResponse(1L, "상품 이름", 50000, "www.imageUrl.com", 2L, 5000);
        given(productService.findById(any())).willReturn(response);

        ResultActions results = mvc.perform(get("/api/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("product-get-one",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상품의 식별자"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("상품의 이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("상품 이미지 url"),
                                fieldWithPath("cartId").type(JsonFieldType.NUMBER).description("유저가 로그인했으면 장바구니 식별자, 안했으면 null").optional(),
                                fieldWithPath("quantity").type(JsonFieldType.NUMBER).description("유저가 로그인했으면 장바구니에 담은 수량, 안했으면 0").optional()
                        )
                ));
    }

    @DisplayName("상품 삭제 문서화")
    @Test
    void deleteTest() throws Exception {
        doNothing().when(productService).deleteById(1L);

        ResultActions results = mvc.perform(delete("/api/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("product-delete"));
    }
}

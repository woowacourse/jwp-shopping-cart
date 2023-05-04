package cart.controller;

import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.helper.RestDocsHelper;
import cart.exception.ForbiddenException;
import cart.service.CartService;
import cart.service.dto.ProductResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(CartRestController.class)
class CartRestControllerTest extends RestDocsHelper {

    private String authorization;

    @MockBean
    private CartService cartService;

    @BeforeEach
    void setUp() {
        authorization = "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=";
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCart_success() throws Exception {
        // given
        when(cartService.addCart(any(), any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/cart/{productId}", 1L)
                .header("Authorization", authorization)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isCreated(),
                header().string("Location", "/cart/me"))
            .andDo(
                documentationResultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("인증 정보")),
                    responseHeaders(
                        headerWithName("Location").description("사용자 장바구니 정보 URI")
                    )
                ));
    }

    @Test
    @DisplayName("장바구니 상품 추가 시 인증되지 않은 사용자면 예외가 발생한다.")
    void addCart_fail() throws Exception {
        mockMvc.perform(post("/cart/{productId}", 1L)
                .header("Authorization", "")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isUnauthorized(),
                jsonPath("$.errorMessage", contains("인증되지 않은 사용자입니다.")))
            .andDo(
                documentationResultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("잘못된 인증 정보"))
                )
            );
    }

    @Test
    @DisplayName("사용자의 장바구니 정보를 조회한다.")
    void getCartByMember() throws Exception {
        // given
        final ProductResponse chickenDto = new ProductResponse(1L, "치킨",
            "chicken_image_url", 20000, "KOREAN");
        final ProductResponse steakDto = new ProductResponse(2L, "스테이크",
            "steak_image_url", 40000, "WESTERN");
        final List<ProductResponse> productResponses = List.of(chickenDto, steakDto);
        when(cartService.getProductsByMemberEmail(any())).thenReturn(productResponses);

        // when, then
        mockMvc.perform(get("/cart/me")
                .header("Authorization", authorization))
            .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE),
                jsonPath("$.productResponses[0].id").value(1L),
                jsonPath("$.productResponses[0].name").value("치킨"),
                jsonPath("$.productResponses[0].imageUrl").value("chicken_image_url"),
                jsonPath("$.productResponses[0].price").value(20000),
                jsonPath("$.productResponses[0].category").value("KOREAN"),
                jsonPath("$.productResponses[1].id").value(2L),
                jsonPath("$.productResponses[1].name").value("스테이크"),
                jsonPath("$.productResponses[1].imageUrl").value("steak_image_url"),
                jsonPath("$.productResponses[1].price").value(40000),
                jsonPath("$.productResponses[1].category").value("WESTERN"))
            .andDo(
                documentationResultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("인증 정보")),
                    responseFields(
                        fieldWithPath("productResponses[0].id").description("상품 아이디"),
                        fieldWithPath("productResponses[0].name").description("상품 이름"),
                        fieldWithPath("productResponses[0].imageUrl").description("상품 이미지 URL"),
                        fieldWithPath("productResponses[0].price").description("상품 가격"),
                        fieldWithPath("productResponses[0].category").description("상품 카테고리"))
                )
            );
    }

    @Test
    @DisplayName("사용자의 장바구니 상품을 제거한다.")
    void deleteCart_success() throws Exception {
        // given
        doNothing().when(cartService).deleteCart(any(), any());

        // when, then
        mockMvc.perform(delete("/cart/{productId}", 1L)
                .header("Authorization", authorization)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(
                documentationResultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("인증 정보"))
                )
            );
    }

    @Test
    @DisplayName("장바구니 상품 제거 시 인증되지 않은 사용자면 예외가 발생한다.")
    void deleteCart_unauthorized() throws Exception {
        mockMvc.perform(delete("/cart/{productId}", 1L)
                .header("Authorization", "")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isUnauthorized(),
                jsonPath("$.errorMessage", contains("인증되지 않은 사용자입니다.")))
            .andDo(
                documentationResultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("잘못된 인증 정보"))
                )
            );
    }

    @Test
    @DisplayName("장바구니 상품 제거 시 권한이 없는 사용자면 예외가 발생한다.")
    void deleteCart_forbidden() throws Exception {
        // given
        doThrow(ForbiddenException.class).when(cartService).deleteCart(any(), any());

        // when, then
        mockMvc.perform(delete("/cart/{productId}", 1L)
                .header("Authorization", "Basic dGVzdEBnbWFpbC5jb206dGVzdDEyMzQ=")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isForbidden(),
                jsonPath("$.errorMessage", contains("권한이 없습니다.")))
            .andDo(
                documentationResultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("다른 사용자의 인증 정보"))
                )
            );
    }
}

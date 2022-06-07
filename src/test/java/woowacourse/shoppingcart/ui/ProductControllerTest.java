package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.restdocs.RestDocsUtils.getRequestPreprocessor;
import static woowacourse.helper.restdocs.RestDocsUtils.getResponsePreprocessor;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("product 컨트롤러 단위테스트")
public class ProductControllerTest extends RestDocsTest {

    @DisplayName("전체 상품을 조회한다.")
    @Test
    void products() throws Exception {
        ProductResponse kimchi = new ProductResponse(1L, "김치", 1000, "image-1.com");
        ProductResponse paKimchi = new ProductResponse(2L, "파김치", 1200, "image-2.com");

        given(productService.findProducts()).willReturn(List.of(kimchi, paKimchi));
        final ResultActions resultActions = mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());

        //docs
        resultActions.andDo(document("products-findAll",
                getResponsePreprocessor(),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].name").type(STRING).description("제품명"),
                        fieldWithPath("[].price").type(NUMBER).description("가격"),
                        fieldWithPath("[].imageUrl").type(STRING).description("이미지 url")
                )));
    }

    @DisplayName("새 상품을 추가한다.")
    @Test
    void add() throws Exception {
        ProductRequest productRequest = new ProductRequest("김치", 1000, "image.com");

        given(productService.addProduct(any(ProductRequest.class))).willReturn(1L);
        final ResultActions resultActions = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/products/1"));

        //docs
        resultActions.andDo(document("products-create",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestFields(
                        fieldWithPath("name").type(STRING).description("제품명"),
                        fieldWithPath("price").type(NUMBER).description("가격"),
                        fieldWithPath("imageUrl").type(STRING).description("이미지 url")
                )));
    }

    @DisplayName("새 상품 추가시 제품명이 없어 실패한다.")
    @Test
    void addNameEmpty() throws Exception {
        ProductRequest productRequest = new ProductRequest("", 1000, "image.com");
        ErrorResponse response = new ErrorResponse("제품명을 입력해주세요.");
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("새 상품 추가시 가격이 맞지 않아 실패한다.")
    @Test
    void addPriceNotRight() throws Exception {
        ProductRequest productRequest = new ProductRequest("김치", -10, "image.com");

        ErrorResponse response = new ErrorResponse("0원 이상의 금액을 입력해주세요.");
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("새 상품 추가시 이미지가 존재하지 않아 실패한다.")
    @Test
    void addImageUrlNotBlank() throws Exception {
        ProductRequest productRequest = new ProductRequest("김치", 1000, "");
        ErrorResponse response = new ErrorResponse("이미지 주소에는 공백이 허용되지 않습니다.");
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("단일 상품을 조회한다.")
    @Test
    void product() throws Exception {
        ProductResponse kimchi = new ProductResponse(1L, "김치", 1000, "image-1.com");

        given(productService.findProductById(anyLong())).willReturn(kimchi);
        final ResultActions resultActions = mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(kimchi)));

        //docs
        resultActions.andDo(document("products-find",
                getResponsePreprocessor(),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("id"),
                        fieldWithPath("name").type(STRING).description("제품명"),
                        fieldWithPath("price").type(NUMBER).description("가격"),
                        fieldWithPath("imageUrl").type(STRING).description("이미지 url")
                )));
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteProductById(anyLong());
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent())
                .andDo(document("products-delete"));
    }
}

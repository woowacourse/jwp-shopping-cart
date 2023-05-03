package cart.controller.rest;

import cart.auth.AuthenticationPrincipalArgumentResolver;
import cart.auth.BasicAuthInterceptor;
import cart.dto.LoginDto;
import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;
    @MockBean
    AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    @MockBean
    BasicAuthInterceptor basicAuthInterceptor;

    @BeforeEach
    void setUp() throws Exception{
        when(authenticationPrincipalArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn(new LoginDto("1", "2"));
        when(basicAuthInterceptor.preHandle(any(), any(), any()))
                .thenReturn(true);
    }


    @Test
    @DisplayName("get 요청시 ok 상태 코드를 반환하고 id에 해당하는 Product를 조회한다")
    void getProductTest() throws Exception {
        Long productId = 5L;

        mockMvc.perform(get("/products/" + productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, Mockito.times(1)).getProduct(productId);
    }

    @Test
    @DisplayName("get 요청시 ok 상태 코드를 반환하고 모든 Product를 조회한다")
    void getProductsTest() throws Exception {
        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, Mockito.times(1)).getAllProducts();
    }

    @Test
    @DisplayName("post 요청시 created 상태 코드를 반환하고 ProductService로 Product를 생성하는지 검증한다")
    void createProductTest() throws Exception {
        ProductRequest request = new ProductRequest("테스트", BigDecimal.valueOf(1000), "http://testtest");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());

        verify(productService, Mockito.times(1)).createProduct(refEq(request));
    }

    @Test
    @DisplayName("put 요청시 no-content 상태 코드를 반환하고 ProductService로 Product를 update하는지 검증한다")
    void updateProductTest() throws Exception {
        Long productId = 3L;
        var request = new ProductUpdateRequest(productId, "updated product", new BigDecimal("5000"), "test-url");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).updateProduct(refEq(request));
    }

    @Test
    @DisplayName("delete 요청시 no-content를 반환하고 ProductService로 Product를 delete하는지 검증한다")
    void deleteProductTest() throws Exception {
        Long productId = 7L;

        mockMvc.perform(delete("/products/" + productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(productId);
    }
}

package cart.controller;

import cart.controller.api.CartController;
import cart.controller.dto.ProductResponse;
import cart.domain.Product;
import cart.service.CartService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    void setup() {
        // mockMvc 설정
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("/cart/products GET 요청이 정상적으로 작동한다.")
    @Test
    void getProducts() throws Exception {
        // given
        Product product = new Product(1L, "치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        ProductResponse productResponse = ProductResponse.from(product);

        given(cartService.findByEmail(anyString())).willReturn(List.of(productResponse));

        // when, then
        MvcResult mvcResult = mockMvc.perform(get("/cart/products")
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE="))
                .andExpect(status().isOk())
                .andReturn();

        List<ProductResponse> responses = objectMapper.readValue(
                contentToString(mvcResult),
                new TypeReference<>() {
                }
        );

        assertThat(responses.get(0))
                .usingRecursiveComparison()
                .isEqualTo(productResponse);
    }

    private String contentToString(MvcResult mvcResult) throws UnsupportedEncodingException {
        return mvcResult.getResponse().getContentAsString();
    }

    @DisplayName("/cart/products POST 요청이 정상적으로 작동한다.")
    @Test
    void addProduct() throws Exception {
        // given
        doNothing().when(cartService).save(anyString(), anyLong());

        // when, then
        mockMvc.perform(post("/cart/products")
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(1L)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @DisplayName("/cart/products/{productId} DELETE 요청이 정상적으로 작동한다.")
    @Test
    void deleteProduct() throws Exception {
        // given
        doNothing().when(cartService).save(anyString(), anyLong());

        // when, then
        mockMvc.perform(delete("/cart/products/" + 1)
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductRegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("registerProduct() : 물품을 생성할 수 있다.")
    void test_registerProduct() throws Exception {
        //given
        final String name = "피자";
        final int price = 10000;
        final String imageUrl = "imageUrl";

        final ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest(name, price, imageUrl);

        //when
        doNothing()
                .when(productService)
                .registerProduct(any());

        final String requestBody = objectMapper.writeValueAsString(productRegisterRequest);

        //then
        mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andDo(print())
               .andExpect(status().isCreated());
    }
}

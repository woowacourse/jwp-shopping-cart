package cart.controller;

import cart.controller.view.ProductController;
import cart.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @DisplayName("`/` URL에는 ")
    @Test
    void showProducts() throws Exception {
        // given
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpectAll(status().isOk());


        // when

        // then
    }
}

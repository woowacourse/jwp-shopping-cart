package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminCartController.class)
class AdminCartControllerTest {

    @MockBean
    private ProductDao productDao;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("/admin/products으로 POST 요청을 했을 때 admin template을 반환한다.")
    @Test
    void createProduct() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "image.url", 10000);

        // when, then
        mockMvc.perform(post("/admin/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @DisplayName("/admin으로 GET 요청을 했을 때 admin template을 반환한다.")
    @Test
    void findAllProducts() throws Exception {
        // given
        Product product = new Product(1, "치킨", "image.img", 10000);
        given(productDao.findAll()).willReturn(List.of(product));

        // when, then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", List.of(product)))
                .andExpect(view().name("admin"));
    }

    @DisplayName("/admin/products/{productId}으로 PUT 요청을 했을 때 admin template을 반환한다.")
    @Test
    void modifyProduct() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "image.url", 10000);

        // when, then
        mockMvc.perform(put("/admin/products/{productId}", "1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @DisplayName("/admin/products/{productId}으로 DELETE 요청을 했을 때 admin template을 반환한다.")
    @Test
    void removeProduct() throws Exception {
        // when, then
        mockMvc.perform(delete("/admin/products/{productId}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }
}
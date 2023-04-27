package cart.controller;

import cart.domain.Product;
import cart.dto.request.ProductSaveRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @Test
    void saveProduct는_상품을_저장하고_created상태코드를_반환한다() throws Exception {
        mockMvc.perform(post("/product")
                        .content(objectMapper.writeValueAsBytes(new ProductSaveRequest("pd1", "image", 2000L)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void findAllProducts는_모든_상품들을_조회한다() throws Exception {
        //given
        given(productService.findAllProducts())
                .willReturn(List.of(new Product(2L, "pdpd2", "image2", 15000)));

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(2L))
                .andExpect(jsonPath("$[0].name").value("pdpd2"))
                .andExpect(jsonPath("$[0].image").value("image2"))
                .andExpect(jsonPath("$[0].price").value(15000));
    }

    @Test
    void updateProduct() throws Exception {
        //todo : mockBean인 서비스에 동작을 설정하지 않았는데(save, update),컨트롤러 메서드가 정상동작함
        mockMvc.perform(put("/product")
                        .content(objectMapper.writeValueAsBytes(new Product(2L, "pdpd2", "image2", 15000L)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        //todo : PathVariable을 어떻게 넣어줄 수 있나요?
        mockMvc.perform(delete("/product/2"))
                .andExpect(status().isOk());
    }
}

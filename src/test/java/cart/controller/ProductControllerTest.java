package cart.controller;

import cart.controller.dto.ModifyProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends ControllerUnitTest {


    @Test
    void 상품을_추가한다() throws Exception {
        given(productDao.add(any()))
                .willReturn(1L);
        final ModifyProductRequest request = new ModifyProductRequest("상품1", 1000, "url");
        final String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated());
    }

    @Test
    void 상품을_수정한다() throws Exception {
        final ModifyProductRequest request = new ModifyProductRequest("상품1", 1000, "url");
        final String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/product/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    void 상품을_삭제한다() throws Exception {
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isOk());
    }
}

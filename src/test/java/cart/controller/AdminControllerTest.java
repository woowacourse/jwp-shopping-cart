package cart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.product.service.ProductService;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("어드민 페이지 반환을 테스트한다.")
    public void testHome() throws Exception {
        //given
        when(productService.findAll())
            .thenReturn(null);

        //when
        //then
        mockMvc.perform(get("/admin"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품을 추가한다.")
    public void testAdd() throws Exception {
        //given
        final String request = objectMapper.writeValueAsString(
            new ProductCreateRequest("연필", 1000, "imageUrl"));

        //when
        //then
        mockMvc.perform(post("/admin/products")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("상품을 수정한다.")
    public void testUpdate() throws Exception {
        //given
        final String request = objectMapper.writeValueAsString(
            new ProductUpdateRequest(1L, "연필", 1000, "imageUrl"));

        //when
        //then
        mockMvc.perform(patch("/admin/products/1")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    public void testDelete() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/admin/products/1"))
            .andDo(print())
            .andExpect(status().isNoContent());
    }
}

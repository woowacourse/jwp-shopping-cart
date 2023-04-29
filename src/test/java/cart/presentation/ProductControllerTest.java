package cart.presentation;

import cart.application.ProductCRUDApplication;
import cart.presentation.dto.ProductDto;
import cart.presentation.dto.ProductIdDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductCRUDApplication productCRUDApplication;


    @Test
    @DisplayName("product/create로 POST 요청을 보낼 수 있다")
    void test_create_request() throws Exception {
        // given
        willDoNothing().given(productCRUDApplication).create(any(ProductDto.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1));

        // when
        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("product/read로 GET 요청을 보낼 수 있다")
    void test_read_request() throws Exception {
        // given
        given(productCRUDApplication.readAll()).willReturn(null);

        // when
        mockMvc.perform(get("/product/read"))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("product/update로 POST 요청을 보낼 수 있다")
    void test_update_request() throws Exception {
        // given
        willDoNothing().given(productCRUDApplication).update(any(ProductDto.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1)
        );

        // when
        mockMvc.perform(post("/product/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

                // then
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("product/delete로 DELETE 요청을 보낼 수 있다")
    void test_delete_request() throws Exception {
        // given
        willDoNothing().given(productCRUDApplication).delete(any(ProductIdDto.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1)
        );

        // when
        mockMvc.perform(delete("/product/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

                // then
                .andExpect(status().isOk());
    }
}

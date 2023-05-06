package cart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.helper.ControllerTestHelper;
import cart.service.ProductService;
import cart.service.dto.ProductResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
class AdminControllerTest extends ControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("어드민 상품 리스트 페이지를 조회한다")
    @Test
    void getProducts() throws Exception {
        // given
        final List<ProductResponse> productResponses = List.of(
            new ProductResponse(1L, "치킨", "chickenUrl", 20000, "KOREAN"),
            new ProductResponse(2L, "초밥", "chobobUrl", 30000, "JAPANESE"),
            new ProductResponse(3L, "스테이크", "steakUrl", 40000, "WESTERN")
        );
        when(productService.getProducts()).thenReturn(productResponses);

        // when, then
        mockMvc.perform(get("/admin")
                .contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }
}

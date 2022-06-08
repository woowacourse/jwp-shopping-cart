package woowacourse.shoppingcart.ui;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponses;

@WebAppConfiguration
@WebMvcTest(controllers = {ProductController.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private ProductService productService;

    @DisplayName("size와 page를 입력하면 page부터 size개수만큼 제품의 정보를 반환한다.")
    @Test
    void products() throws Exception {
        // given
        List<Product> productResponses = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            productResponses.add(new Product("name" + i, i * 1000, "imageUrl" + i));
        }

        int size = 10;
        int page = 3;
        int from = page * size;
        given(productService.findProducts(size, page))
                .willReturn(new ProductResponses(productResponses.subList(from, from + size)));

        mockMvc.perform(get("/products?size=" + size + "&page=" + page))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("['products']", hasSize(size)))
                .andExpect(jsonPath("['products'][0].name").value("name31"))
                .andExpect(jsonPath("['products'][0].price").value(31000))
                .andExpect(jsonPath("['products'][0].imageUrl").value("imageUrl31"))
                .andDo(print());
    }
}

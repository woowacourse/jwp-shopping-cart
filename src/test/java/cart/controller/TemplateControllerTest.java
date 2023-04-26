package cart.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import cart.dto.ProductResponse;
import cart.dto.ResponseFixture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest
class TemplateControllerTest extends AbstractProductControllerTest {

    @Test
    void 상품_조회_테스트() throws Exception {
        final List<ProductResponse> expected = List.of(
                ResponseFixture.NUNU_RESPONSE,
                ResponseFixture.ODO_RESPONSE
        );
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("products", equalTo(expected)));
    }

    @Test
    void 어드민_페이지_조회_테스트() throws Exception {
        final List<ProductResponse> expected = List.of(
                ResponseFixture.NUNU_RESPONSE,
                ResponseFixture.ODO_RESPONSE
        );
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("products", equalTo(expected)));
    }
}

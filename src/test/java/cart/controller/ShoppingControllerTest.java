package cart.controller;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import cart.service.ShoppingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingController.class)
class ShoppingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingService shoppingService;

    @Test
    void index() throws Exception {
        // given
        final List<ProductDto> productDtos = List.of(
                new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                new ProductDto(2L, "초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                new ProductDto(3L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
        );
        when(shoppingService.getProducts()).thenReturn(productDtos);

        // when, then
        mockMvc.perform(get("/")
                        .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

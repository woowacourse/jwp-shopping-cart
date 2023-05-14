package cart.product.controller;

import cart.ControllerTest;
import cart.product.domain.Product;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cart.DummyData.INITIAL_PRODUCT_ONE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AdminControllerTest extends ControllerTest {

    private static final String path = "/admin";

    @Test
    void 상품_전체_목록을_조회하면_상태코드_200을_반환하는지_확인한다() throws Exception {
        final List<Product> products = List.of(INITIAL_PRODUCT_ONE);

        when(productService.findAll())
                .thenReturn(products);

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }
}

package cart.controller.home;

import cart.config.WebConfig;
import cart.config.admin.Base64AdminAccessInterceptor;
import cart.config.auth.Base64AuthInterceptor;
import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(value = HomePageController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {Base64AuthInterceptor.class, Base64AdminAccessInterceptor.class, WebConfig.class})
})
public class HomePageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductManagementService productManagementService;

    @Test
    void 상품_목록_페이지를_조회한다() throws Exception {
        List<ProductDto> products = new ArrayList<>();
        given(productManagementService.findAllProduct())
                .willReturn(products);

        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products))
                .andExpect(status().isOk());
    }
}

package cart.controller.cart;

import cart.config.WebConfig;
import cart.config.admin.Base64AdminAccessInterceptor;
import cart.config.auth.Base64AuthInterceptor;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(value = CartPageController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {Base64AuthInterceptor.class, Base64AdminAccessInterceptor.class, WebConfig.class})
})
public class CartPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 장바구니_페이지에_접속한다() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk());
    }
}

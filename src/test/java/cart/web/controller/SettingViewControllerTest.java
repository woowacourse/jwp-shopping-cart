package cart.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.domain.product.TestFixture;
import cart.domain.user.service.CartUserService;
import cart.domain.user.service.dto.CartUserDto;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@MockBean(BasicAuthorizedUserArgumentResolver.class)
@WebMvcTest(SettingViewController.class)
class SettingViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CartUserService cartUserService;


    @DisplayName("/settings 경로 요청 시, settings.html을 반환한다.")
    @Test
    void loadSettingPage() throws Exception {
        Mockito.when(cartUserService.getAllCartUsers())
                .thenReturn(
                        List.of(CartUserDto.from(TestFixture.CART_USER_A), CartUserDto.from(TestFixture.CART_USER_B)));

        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cartUsers", hasSize(2)))
                .andExpect(view().name("settings"))
                .andDo(print());
    }
}

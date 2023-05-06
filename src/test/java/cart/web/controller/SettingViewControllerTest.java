package cart.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.domain.product.TestFixture;
import cart.domain.user.service.dto.CartUserResponseDto;
import cart.domain.user.usecase.FindAllCartUsersUseCase;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import cart.web.controller.index.SettingViewController;
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
    private FindAllCartUsersUseCase findAllCartUserService;


    @DisplayName("/settings 경로 요청 시, settings.html을 반환한다.")
    @Test
    void loadSettingPage() throws Exception {
        Mockito.when(findAllCartUserService.getAllCartUsers())
                .thenReturn(
                        List.of(CartUserResponseDto.from(TestFixture.CART_USER_A),
                                CartUserResponseDto.from(TestFixture.CART_USER_B)));

        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cartUsers", hasSize(2)))
                .andExpect(view().name("settings"))
                .andDo(print());
    }
}

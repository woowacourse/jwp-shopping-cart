package cart.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import cart.controller.AbstractProductControllerTest;
import cart.domain.user.User;
import org.junit.jupiter.api.Test;

class CartDeleteControllerTest extends AbstractProductControllerTest {

    @Test
    void 장바구니_삭제_테스트() throws Exception {
        given(userRepository.findByEmail(any())).willReturn(Optional.of(new User("a@a.com", "password1")));
        mockMvc.perform(delete("/carts/1")
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE="))

                .andExpect(status().isNoContent());
    }
}

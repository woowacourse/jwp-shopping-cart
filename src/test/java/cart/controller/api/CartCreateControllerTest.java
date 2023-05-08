package cart.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import cart.controller.AbstractProductControllerTest;
import cart.controller.dto.CartRequest;
import cart.controller.dto.CartResponse;
import cart.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class CartCreateControllerTest extends AbstractProductControllerTest {

    @Test
    void 장바구니_추가_테스트() throws Exception {
        given(userRepository.findByEmail(any())).willReturn(Optional.of(new User("a@a.com", "password1")));
        given(cartCreateService.create(anyString(), anyLong())).willReturn(new CartResponse(1L, "a@a.com", 1L));

        final CartRequest cartRequest = new CartRequest(1L);
        final String request = objectMapper.writeValueAsString(cartRequest);
        final String result = objectMapper.writeValueAsString(new CartResponse(1L, "a@a.com", 1L));
        mockMvc.perform(post("/carts")
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }
}

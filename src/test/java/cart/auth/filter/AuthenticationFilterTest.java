package cart.auth.filter;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.AbstractProductControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest
class AuthenticationFilterTest extends AbstractProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new AuthenticationFilter(userRepository))
                .build();
    }

    @Test
    void 인증_헤더_없이_요청을_날리면_예외가_발생한다() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 인증_헤더_있이_요청시_성공한다() throws Exception {
        final String email = "test@naver.com";
        final String password = "1234";
        final String basicAuthHeader = "Basic " + Base64Utils.encodeToString((email + ":" + password).getBytes());
        given(userRepository.existsByEmailAndPassword(email, password)).willReturn(true);

        mockMvc.perform(get("/")
                        .header("Authorization", basicAuthHeader))

                .andExpect(status().isOk());
    }
}

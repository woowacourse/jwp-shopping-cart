package woowacourse.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.service.LoginService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.support.Encryptor;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginService authService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private Encryptor encryptor;

    @MockBean
    private CustomerDao customerDao;

    @Test
    void 로그인() throws Exception {
        // given
        TokenRequest request = new TokenRequest("ellie", "12345678");
        String requestContent = objectMapper.writeValueAsString(request);

        // mocking
        given(authService.login(any(TokenRequest.class))).willReturn(new TokenResponse("fake_token"));

        // when
        ResultActions response = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("accessToken").value("fake_token"));
    }
}

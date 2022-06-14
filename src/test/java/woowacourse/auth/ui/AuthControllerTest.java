package woowacourse.auth.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.config.RestDocsConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("인증 API 문서화")
@AutoConfigureRestDocs
@WebMvcTest(AuthController.class)
@Import(RestDocsConfig.class)
class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("로그인 후, 토큰 발급 문서화")
    @Test
    void login() throws Exception {
        TokenRequest request = new TokenRequest("giron", "a1A!qe213");
        TokenResponse response = new TokenResponse("mad@asdaj$sd1314.142@ask41a4.mjs3nzs@a");

        given(jwtTokenProvider.createToken(any())).willReturn("accessToken");
        given(authService.login(any())).willReturn(response);

        ResultActions results = mvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(request)));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("customer-login",
                        requestFields(
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀 번호")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰")
                        )
                ));
    }
}

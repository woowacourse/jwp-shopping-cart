package woowacourse.shoppingcart.unit.auth.ui;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.auth.dto.LoginRequest;
import woowacourse.shoppingcart.auth.dto.LoginResponse;
import woowacourse.shoppingcart.auth.exception.badrequest.InvalidLoginException;
import woowacourse.shoppingcart.unit.ControllerTest;

@DisplayName("AuthController 단위 테스트")
class AuthControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인 요청에 성공하면 JWT 토큰을 발급해서 반환한다.")
    void login_validForm_200() throws Exception {
        // given
        final String accessToken = "fake-token";
        final LoginRequest request = new LoginRequest("email@email.com", "1q2w3e4r");
        given(authService.login(request))
                .willReturn(accessToken);

        final String json = objectMapper.writeValueAsString(request);

        final LoginResponse response = new LoginResponse(accessToken);
        final String expected = objectMapper.writeValueAsString(response);

        // when
        final ResultActions perform = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        final String body = perform
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body).isEqualTo(expected);

        // docs
        perform.andDo(document("login",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호")
                ),
                responseFields(
                        fieldWithPath("accessToken").type(STRING).description("토큰")
                )
        ));
    }

    @Test
    @DisplayName("로그인에 실패하면 400을 반환한다.")
    void login_notExistEmail_400() throws Exception {
        // given
        final LoginRequest request = new LoginRequest("email@email.com", "1q2w3e4r");
        final String json = objectMapper.writeValueAsString(request);

        given(authService.login(request))
                .willThrow(new InvalidLoginException());

        // when
        final ResultActions perform = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1002"))
                .andExpect(jsonPath("message").value("로그인에 실패했습니다."));

        // docs
        perform.andDo(document("login-fail",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호")
                ),
                responseFields(
                        fieldWithPath("errorCode").type(STRING).description("에러코드"),
                        fieldWithPath("message").type(STRING).description("에러 메시지")
                )
        ));
    }
}

package woowacourse.member.ui;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.fixture.MemberFixture.BEARER;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.TOKEN;
import static woowacourse.helper.fixture.MemberFixture.createMemberRegisterRequest;
import static woowacourse.helper.restdocs.RestDocsUtils.getRequestPreprocessor;
import static woowacourse.helper.restdocs.RestDocsUtils.getResponsePreprocessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.dto.MemberResponse;

@DisplayName("멤버 컨트롤러 단위테스트")
public class MemberControllerTest extends RestDocsTest {

    @DisplayName("멤버가 회원가입에 성공한다.")
    @Test
    void register() throws Exception {
        MemberRegisterRequest request = createMemberRegisterRequest(EMAIL, PASSWORD, NAME);

        given(memberService.save(any(MemberRegisterRequest.class))).willReturn(1L);

        ResultActions resultActions = mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        //docs
        resultActions.andDo(document("member-register",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호"),
                        fieldWithPath("name").type(STRING).description("이름")
                )));
    }

    @DisplayName("멤버를 조회한다.")
    @Test
    void getMemberInformation() throws Exception {
        MemberResponse memberResponse = new MemberResponse(1L, EMAIL, NAME);
        given(memberService.getMemberInformation(anyLong())).willReturn(memberResponse);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(get("/api/members/me")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(memberResponse)));

        //docs
        resultActions.andDo(document("member-me",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("id"),
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("name").type(STRING).description("이름")
                )));
    }
}

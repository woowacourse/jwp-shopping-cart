package woowacourse.member.ui;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.member.dto.MemberNameUpdateRequest;
import woowacourse.member.dto.MemberPasswordUpdateRequest;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.dto.MemberResponse;
import woowacourse.member.exception.DuplicateMemberEmailException;
import woowacourse.member.exception.EmailNotValidException;

@DisplayName("멤버 컨트롤러 단위테스트")
public class MemberControllerTest extends RestDocsTest {

    @DisplayName("이메일 중복 체크에 성공한다.")
    @Test
    void validateDuplicateEmail() throws Exception {
        doNothing().when(memberService).validateDuplicateEmail(anyString());

        ResultActions resultActions = mockMvc.perform(get("/api/members/duplicate-email?email=" + EMAIL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //docs
        resultActions.andDo(document("member-duplicate-email",
                getRequestPreprocessor(),
                getResponsePreprocessor()));
    }

    @DisplayName("이메일이 중복되어 중복 체크에 실패한다.")
    @Test
    void failedValidateDuplicateEmail() throws Exception {
        doThrow(DuplicateMemberEmailException.class)
                .when(memberService).validateDuplicateEmail(anyString());

        mockMvc.perform(get("/api/members/duplicate-email?email=" + EMAIL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("입력이 형식에 맞지 않아 이메일 중복 체크에 실패한다.")
    @Test
    void failedValidateDuplicateEmailWrongFormat() throws Exception {
        doThrow(EmailNotValidException.class)
                .when(memberService).validateDuplicateEmail(anyString());

        mockMvc.perform(get("/api/members/duplicate-email?email=" + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

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

    @DisplayName("멤버가 회원가입에 실패한다.")
    @ParameterizedTest
    @MethodSource("provideMemberRegisterRequest")
    void failedRegister(MemberRegisterRequest request) throws Exception {
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideMemberRegisterRequest() {
        return Stream.of(
                Arguments.of(createMemberRegisterRequest("email", PASSWORD, NAME)),
                Arguments.of(createMemberRegisterRequest(EMAIL, "password", NAME)),
                Arguments.of(createMemberRegisterRequest(EMAIL, PASSWORD, "namenamename"))
        );
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

    @DisplayName("멤버 조회에 실패한다.")
    @Test
    void failedGetMemberInformation() throws Exception {
        given(jwtTokenProvider.validateToken(anyString())).willReturn(false);
        ErrorResponse errorResponse = new ErrorResponse("[ERROR] 토큰이 올바르지 않습니다.");

        mockMvc.perform(get("/api/members/me")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
    }

    @DisplayName("이름을 수정한다.")
    @Test
    void updateName() throws Exception {
        MemberNameUpdateRequest memberNameUpdateRequest = new MemberNameUpdateRequest(NAME);
        doNothing().when(memberService).updateName(anyLong(), any(MemberNameUpdateRequest.class));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(put("/api/members/me/name")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberNameUpdateRequest)))
                .andExpect(status().isOk());

        // docs
        resultActions.andDo(document("member-name-update",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("name").description("수정할 이름")
                )));

    }

    @DisplayName("입력 형식이 잘못되어 이름 수정에 실패한다.")
    @Test
    void failedUpdateName() throws Exception {
        MemberNameUpdateRequest memberNameUpdateRequest = new MemberNameUpdateRequest("namenamename");

        doNothing().when(memberService).updateName(anyLong(), any(MemberNameUpdateRequest.class));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(put("/api/members/me/name")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberNameUpdateRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("토큰이 유효하지 않아 이름 수정에 실패한다.")
    @Test
    void failedUpdateNameWithNotValidToken() throws Exception {
        MemberNameUpdateRequest memberNameUpdateRequest = new MemberNameUpdateRequest(NAME);
        given(jwtTokenProvider.validateToken(anyString())).willReturn(false);
        ErrorResponse errorResponse = new ErrorResponse("[ERROR] 토큰이 올바르지 않습니다.");

        mockMvc.perform(put("/api/members/me/name")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberNameUpdateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void updatePassword() throws Exception {
        MemberPasswordUpdateRequest memberPasswordUpdateRequest =
                new MemberPasswordUpdateRequest(PASSWORD, "NewPassword1!");
        doNothing().when(memberService).updatePassword(anyLong(), any(MemberPasswordUpdateRequest.class));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(put("/api/members/me/password")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPasswordUpdateRequest)))
                .andExpect(status().isOk());

        // docs
        resultActions.andDo(document("member-password-update",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("oldPassword").description("이전 비밀번호"),
                        fieldWithPath("newPassword").description("현재 비밀번호")
                )));
    }

    @DisplayName("입력 형식이 잘못되어 비밀번호 수정을 실패한다.")
    @Test
    void failedUpdatePassword() throws Exception {
        MemberPasswordUpdateRequest memberPasswordUpdateRequest =
                new MemberPasswordUpdateRequest(PASSWORD, "wrong");
        doNothing().when(memberService).updatePassword(anyLong(), any(MemberPasswordUpdateRequest.class));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(put("/api/members/me/password")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPasswordUpdateRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("토큰이 유효하지 않아 비밀번호 수정을 실패한다.")
    @Test
    void failedUpdatePasswordWithNotValidToken() throws Exception {
        MemberPasswordUpdateRequest memberPasswordUpdateRequest =
                new MemberPasswordUpdateRequest(PASSWORD, "NewPassword1!");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        ErrorResponse errorResponse = new ErrorResponse("[ERROR] 토큰이 올바르지 않습니다.");

        mockMvc.perform(put("/api/members/me/password")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPasswordUpdateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
    }

    @DisplayName("회원을 탈퇴한다.")
    @Test
    void deleteMember() throws Exception {
        doNothing().when(memberService).deleteById(anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(delete("/api/members/me")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isNoContent());

        resultActions.andDo(document("member-delete",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                )));
    }

    @DisplayName("회원 탈퇴에 실패한다.")
    @Test
    void failedDeleteMember() throws Exception {
        given(jwtTokenProvider.validateToken(anyString())).willReturn(false);
        ErrorResponse errorResponse = new ErrorResponse("[ERROR] 토큰이 올바르지 않습니다.");

        mockMvc.perform(delete("/api/members/me")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
    }
}

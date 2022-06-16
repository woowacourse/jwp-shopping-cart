package woowacourse.shoppingcart.unit.customer.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.customer.dto.CustomerResponse;
import woowacourse.shoppingcart.customer.dto.CustomerUpdationRequest;
import woowacourse.shoppingcart.customer.exception.badrequest.DuplicateEmailException;
import woowacourse.shoppingcart.unit.ControllerTest;

@DisplayName("CustomerController 단위 테스트")
class CustomerControllerTest extends ControllerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(
                1L,
                "rick",
                "email@email.com",
                HASH
        );
    }

    @Test
    @DisplayName("유효한 양식으로 회원가입에 성공한다.")
    void create_validForm_204() throws Exception {
        // given
        final CustomerCreationRequest request = new CustomerCreationRequest(
                "email@email.com",
                "1q2w3e4r",
                "rick"
        );
        final String json = objectMapper.writeValueAsString(request);

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(document("create-user",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호"),
                        fieldWithPath("nickname").type(STRING).description("닉네임")
                )
        ));
    }

    @ParameterizedTest(name = "잘못된 {3}으로 회원가입을 요청하면 400을 반환한다.")
    @CsvSource(value = {
            "email:email#email.com:1q2w3e4r:rick:이메일 양식",
            "password1:email@email.com:1q2w3e:rick:비밀번호 양식",
            "password2:email@email.com:12345678:rick:비밀번호 양식",
            "nickname:email@email.com:1q2w3e4r:릭:닉네임 양식"
    }, delimiter = ':')
    void create_invalidForm_400(final String docName, final String email, final String password, final String nickname,
                                final String message)
            throws Exception {
        // given
        final CustomerCreationRequest request = new CustomerCreationRequest(
                email,
                password,
                nickname
        );
        final String json = objectMapper.writeValueAsString(request);

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1000"))
                .andExpect(jsonPath("message").value(message + "이 잘못 되었습니다."));

        // docs
        perform.andDo(document("create-user-fail-" + docName,
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호"),
                        fieldWithPath("nickname").type(STRING).description("닉네임")
                ),
                responseFields(
                        fieldWithPath("errorCode").type(STRING).description("에러 코드"),
                        fieldWithPath("message").type(STRING).description("에러 메시지")
                )
        ));
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입을 요청하믄 400을 반환한다.")
    void create_duplicateEmail_400() throws Exception {
        // given
        final CustomerCreationRequest request = new CustomerCreationRequest("rick@gmail.com", "1q2w3e4r", "rick");
        final String json = objectMapper.writeValueAsString(request);

        given(customerService.create(request))
                .willThrow(new DuplicateEmailException());

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1001"))
                .andExpect(jsonPath("message").value("이메일이 중복입니다."));

        // docs
        perform.andDo(document("create-user-fail-duplicate-email",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호"),
                        fieldWithPath("nickname").type(STRING).description("닉네임")
                ),
                responseFields(
                        fieldWithPath("errorCode").type(STRING).description("에러 코드"),
                        fieldWithPath("message").type(STRING).description("에러 메시지")
                )
        ));
    }

    @Test
    @DisplayName("로그인한 Customer의 정보를 반환한다.")
    void getMe_validToken_200() throws Exception {
        // given
        final String accessToken = "fake-token";
        getLoginCustomerByToken(accessToken, customer);

        final CustomerResponse response = CustomerResponse.from(customer);
        final String expected = objectMapper.writeValueAsString(response);

        // when
        final ResultActions perform = mockMvc.perform(get("/users/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        final String body = perform.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body).isEqualTo(expected);

        // docs
        perform.andDo(document("get-me",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("nickname").type(STRING).description("닉네임")
                )
        ));
    }

    @Test
    @DisplayName("로그인한 Customer의 정보를 조회하는 요청에 토큰이 존재하지 않으면 401을 반환한다.")
    void getMe_notExistToken_401() throws Exception {
        // when
        final ResultActions perform = mockMvc.perform(get("/users/me")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("errorCode").value("3000"))
                .andExpect(jsonPath("message").value("유효하지 않은 토큰입니다."));

        // docs
        perform.andDo(document("get-me-empty-token",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                        fieldWithPath("errorCode").type(STRING).description("에러 코드"),
                        fieldWithPath("message").type(STRING).description("에러 메시지")
                )
        ));
    }

    @Test
    @DisplayName("로그인한 Customer의 정보를 조회하는 요청에 토큰이 유효하지 않으면 401을 반환한다.")
    void getMe_invalidToken_401() throws Exception {
        // given
        final String accessToken = "invalid-token";
        given(jwtTokenProvider.validateToken(accessToken))
                .willReturn(false);

        // when
        final ResultActions perform = mockMvc.perform(get("/users/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("errorCode").value("3000"))
                .andExpect(jsonPath("message").value("유효하지 않은 토큰입니다."));

        // docs
        perform.andDo(document("get-me-invalid-token",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("errorCode").type(STRING).description("에러 코드"),
                        fieldWithPath("message").type(STRING).description("에러 메시지")
                )
        ));
    }

    @Test
    @DisplayName("로그인한 Customer를 삭제한다.")
    void deleteMe_validToken_204() throws Exception {
        // given
        final String accessToken = "fake-token";
        getLoginCustomerByToken(accessToken, customer);

        // when
        final ResultActions perform = mockMvc.perform(delete("/users/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(document("delete-me",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                )
        ));
    }

    @Test
    @DisplayName("로그인한 Customer의 정보를 수정한다.")
    void updateMe_validToken_204() throws Exception {
        // given
        final String accessToken = "fake-token";
        getLoginCustomerByToken(accessToken, customer);

        final CustomerUpdationRequest request = new CustomerUpdationRequest("kun", "kunkun1234");
        final String json = objectMapper.writeValueAsString(request);

        // when
        final ResultActions perform = mockMvc.perform(put("/users/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(document("update-me",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                        fieldWithPath("password").type(STRING).description("비밀번호")
                )
        ));
    }
}

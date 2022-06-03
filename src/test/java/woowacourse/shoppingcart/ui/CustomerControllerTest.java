package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;

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
    }

    @ParameterizedTest(name = "잘못된 {3}으로 회원가입을 요청하면 400을 반환한다.")
    @CsvSource(value = {
            "email#email.com:1q2w3e4r:rick:이메일 양식",
            "email@email.com:1q2w3e:rick:비밀번호 양식",
            "email@email.com:12345678:rick:비밀번호 양식",
            "email@email.com:1q2w3e4r:릭:닉네임 양식"
    }, delimiter = ':')
    void create_invalidForm_400(final String email, final String password, final String nickname, final String message)
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
                .andExpect(jsonPath("errorCode").value("998"))
                .andExpect(jsonPath("message").value("유효하지 않은 토큰입니다."));
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
                .andExpect(jsonPath("errorCode").value("998"))
                .andExpect(jsonPath("message").value("유효하지 않은 토큰입니다."));
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
    }
}

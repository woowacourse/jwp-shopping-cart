package woowacourse.auth.ui;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.customer.CustomerRequest;
import woowacourse.auth.dto.customer.CustomerResponse;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.dto.customer.CustomerUpdateResponse;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.support.JwtTokenProvider;

@WebMvcTest({CustomerController.class, AuthService.class})
class CustomerControllerTest {

    private final String email = "123@gmail.com";
    private final String password = "a1234!";
    private final String nickname = "does";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private JwtTokenProvider tokenProvider;

    @DisplayName("회원가입을 한다.")
    @Test
    void signUp() throws Exception {
        // given
        CustomerRequest request = new CustomerRequest(email, password, nickname);
        String requestJson = objectMapper.writeValueAsString(request);
        given(customerService.signUp(any(CustomerRequest.class)))
                .willReturn(new Customer(1L, email, password, nickname));

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new CustomerResponse(email, nickname)))
                );
    }

    @DisplayName("중복된 이메일이 있으면 가입하지 못한다.")
    @Test
    void signUpDuplicatedEmail() throws Exception {
        // given
        CustomerRequest request = new CustomerRequest(email, password, nickname);
        String requestJson = objectMapper.writeValueAsString(request);
        given(customerService.signUp(any(CustomerRequest.class)))
                .willThrow(IllegalArgumentException.class);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("이메일의 형식이 올바르지 못하면 400 반환")
    @ParameterizedTest
    @ValueSource(strings = {"123gmail.com", "123@gmailcom", "123gamilcom"})
    void emailFormatException(String email) throws Exception {
        // given
        CustomerRequest request = new CustomerRequest(email, password, nickname);
        String requestJson = objectMapper.writeValueAsString(request);
        given(customerService.signUp(any(CustomerRequest.class)))
                .willThrow(IllegalArgumentException.class);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("닉네임의 형식이 올바르지 못하면 400 반환")
    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901"})
    void nicknameFormatException(String nickname) throws Exception {
        // given
        CustomerRequest request = new CustomerRequest(email, password, nickname);
        String requestJson = objectMapper.writeValueAsString(request);
        given(customerService.signUp(any(CustomerRequest.class)))
                .willThrow(IllegalArgumentException.class);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호의 형식이 올바르지 못하면 400 반환")
    @ParameterizedTest
    @ValueSource(strings = {"1234", "abasdas", "!@#!@#", "123d213", "asdasd!@#@", "123!@@##!1"})
    void passwordFormatException(String password) throws Exception {
        // given
        CustomerRequest request = new CustomerRequest(email, password, nickname);
        String requestJson = objectMapper.writeValueAsString(request);
        given(customerService.signUp(any(CustomerRequest.class)))
                .willThrow(IllegalArgumentException.class);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("토큰이 없을 때 탈퇴를 하려고 하면 401 반환")
    @Test
    void signOutNotLogin() throws Exception {
        // when
        mockMvc.perform(delete("/customers"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("토큰이 있을 때 회원 탈퇴를 한다.")
    @Test
    void signOutwithToken() throws Exception {
        // given
        String token = "access-token";
        loginCheck(token);

        // when
        mockMvc.perform(delete("/customers")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @DisplayName("토큰이 있을 때 회원정보 수정을 한다.")
    @Test
    void updateCustomer() throws Exception {
        // given
        String token = "access-token";
        Customer loginCustomer = loginCheck(token);
        given(customerService.update(eq(loginCustomer), any(CustomerUpdateRequest.class)))
                .willReturn(new Customer(1L, email, "b1234", "thor"));

        // when
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "thor", "a1234!", "b1234!"
        );
        CustomerUpdateResponse response = new CustomerUpdateResponse("thor");

        mockMvc.perform(patch("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @DisplayName("토큰이 없을 때 회원 정보 수정을 하면 401 반환")
    @Test
    void updateCustomerNotToken() throws Exception {
        // given
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "thor", "a1234!", "b1234!"
        );

        // when
        mockMvc.perform(patch("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    private Customer loginCheck(String token) {
        given(tokenProvider.getPayload(token))
                .willReturn(email);
        given(tokenProvider.validateToken(token))
                .willReturn(true);
        Customer loginCustomer = new Customer(1L, email, password, nickname);
        given(customerService.findByEmail(email))
                .willReturn(loginCustomer);
        return loginCustomer;
    }

    @DisplayName("기존 비밀번호가 다르면 정보를 수정할 수 없다.")
    @Test
    void updateCustomerNotSamePassword() throws Exception {
        // given
        String token = "access-token";
        Customer loginCustomer = loginCheck(token);
        given(customerService.update(eq(loginCustomer), any(CustomerUpdateRequest.class)))
                .willThrow(new InvalidAuthException());

        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "thor", "a1234!", "b1234!"
        );

        // when
        mockMvc.perform(patch("/customers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("회원정보를 조회할 수 있다.")
    @Test
    void findCustomer() throws Exception {
        // given
        String token = "access-token";
        Customer loginCustomer = loginCheck(token);

        // when
        mockMvc.perform(get("/customers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CustomerResponse(loginCustomer))))
                .andExpect(status().isOk());
    }

}

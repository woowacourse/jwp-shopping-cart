package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;
import woowacourse.shoppingcart.dto.TokenRequest;

@WebMvcTest(CustomerController.class)
@Import(JwtTokenProvider.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomerService customerService;

    @DisplayName("회원 가입을 한다.")
    @Test
    void signUp() throws Exception {
        // given
        CustomerRequest request = new CustomerRequest("jo@naver.com", "jojogreen", "1234");

        // when
        when(customerService.signUp(any()))
                .thenReturn(1L);

        // then
        mockMvc.perform(post("/customers/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/customers/1"));
    }

    @DisplayName("로그인을 한다.")
    @Test
    void login() throws Exception {
        // given
        CustomerLoginRequest request = new CustomerLoginRequest("jiwoo@naver.com", "1234");

        // when
        when(customerService.login(any()))
                .thenReturn(new CustomerLoginResponse("token", 1L, "jiwoo@naver.com", "hunch"));

        // then
        mockMvc.perform(post("/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("userId").value("jiwoo@naver.com"))
                .andExpect(jsonPath("nickname").value("hunch"));
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void getProfile() throws Exception {
        // given
        TokenRequest request = new TokenRequest(1L);

        // when
        when(customerService.findById(any()))
                .thenReturn(new CustomerResponse(1L, "jo@naver.com", "jojogreen"));

        // then
        String token = jwtTokenProvider.createToken(String.valueOf(request.getId()));
        mockMvc.perform(get("/auth/customers/profile")
                        .header("Authorization", "Bearer " + token)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("userId").value("jo@naver.com"))
                .andExpect(jsonPath("nickname").value("jojogreen"));
    }

    @DisplayName("내 정보를 수정한다.")
    @Test
    void updateProfile() throws Exception {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("hunch");

        // then
        String token = jwtTokenProvider.createToken(String.valueOf(tokenRequest.getId()));
        mockMvc.perform(patch("/auth/customers/profile")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(updateRequest))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("비밀번호를 변경한다.")
    @Test
    void updatePassword() throws Exception {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest("1234", "2345");

        // then
        String token = jwtTokenProvider.createToken(String.valueOf(tokenRequest.getId()));
        mockMvc.perform(patch("/auth/customers/profile/password")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(passwordChangeRequest))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원 탈퇴한다.")
    @Test
    void withdraw() throws Exception {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);

        // then
        String token = jwtTokenProvider.createToken(String.valueOf(tokenRequest.getId()));
        mockMvc.perform(delete("/auth/customers/profile")
                        .header("Authorization", "Bearer " + token)
                ).andDo(print())
                .andExpect(status().isNoContent());
    }
}

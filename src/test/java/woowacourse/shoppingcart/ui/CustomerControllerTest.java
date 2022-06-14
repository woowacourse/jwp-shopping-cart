package woowacourse.shoppingcart.ui;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.config.LoginCustomerResolver;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.exception.bodyexception.DuplicateEmailException;

@WebMvcTest(controllers = {CustomerController.class})
@MockBean(value = LoginCustomerResolver.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("회원가입이 정상적으로 된 경우 상태코드 204를 반환한다.")
    @Test
    void create_right_200() throws Exception {
        // given
        CustomerCreationRequest request = new CustomerCreationRequest("kun@naver.com", "1q2w3e4r", "kun");

        given(customerService.create(request))
                .willReturn(1L);

        // when, then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("회원 정보 양식이 잘못 되었을 때, 상태코드 400을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "kun#naver.com:12345667a:쿤aa:이메일 양식이 잘못 되었습니다.",
            "kun@naver.com:1234:쿤aa:비밀번호 양식이 잘못 되었습니다.",
            "kun@naver.com:123456677aa:쿤:닉네임 양식이 잘못 되었습니다."}, delimiter = ':')
    void create_wrongForm_400(String email, String password, String nickname, String message) throws Exception {
        //given
        CustomerCreationRequest request = new CustomerCreationRequest(email, password, nickname);

        //when, then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("1000"))
                .andExpect(jsonPath("$.message").value(message));
    }

    @DisplayName("이메일이 중복 되었을 때, 상태코드 400을 반환한다.")
    @Test
    void create_duplicateEmail_400() throws Exception {
        //given
        CustomerCreationRequest request = new CustomerCreationRequest("kun@naver.com", "1q2w3e4r", "kun");

        given(customerService.create(request))
                .willThrow(new DuplicateEmailException());

        //when, then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("1001"))
                .andExpect(jsonPath("$.message").value("이메일이 중복입니다."));
    }
}

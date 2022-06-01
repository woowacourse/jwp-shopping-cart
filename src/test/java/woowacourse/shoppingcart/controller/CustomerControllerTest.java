package woowacourse.shoppingcart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.SignInDto;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.DeleteCustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.UpdateCustomerDto;
import woowacourse.shoppingcart.service.CustomerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private AuthService authService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final long customerId = 1L;
    private final String testEmail = "test@test.com";
    private final String testUsername = "테스트";
    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = jwtTokenProvider.createToken(testEmail);
    }

    @Test
    @DisplayName("이메일, 패스워드, 유저 이름을 받아서 CREATED와 Location 헤더에 리소스 접근 URI를 반환한다.")
    void signUp() throws Exception {
        final SignUpDto signUpDto = new SignUpDto(testEmail, "testtest", testUsername);
        when(customerService.signUp(any(SignUpDto.class))).thenReturn(customerId);

        final MockHttpServletResponse response = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(signUpDto)))
                        .andDo(print())
                        .andReturn()
                        .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).isEqualTo("/api/customers/"+ customerId);
    }

    @Test
    @DisplayName("유저 이름을 받아서 기존 유저의 이름을 수정한 뒤 수정된 유저를 반환한다.")
    void updateCustomer() throws Exception {
        when(customerService.findCustomerByEmail(any(String.class)))
                .thenReturn(new CustomerDto(customerId, testEmail, testUsername));
        when(customerService.updateCustomer(any(Long.class),any(UpdateCustomerDto.class))).thenReturn(new CustomerDto(
                customerId, testEmail,"테스트2"));

        UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto("테스트2");

        final MockHttpServletResponse response = mockMvc.perform(put("/api/customers/" + customerId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(updateCustomerDto)))
                        .andDo(print())
                        .andReturn()
                        .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("URI path에 id를 받아 일치하는 회원을 삭제한다.")
    void deleteCustomer() throws Exception {
        final CustomerDto customerDto = new CustomerDto(customerId, testEmail, testUsername);
        when(customerService.findCustomerByEmail(any(String.class)))
                .thenReturn(customerDto);
        when(authService.login(any(SignInDto.class))).thenReturn(new TokenResponseDto(accessToken, 1000000L, customerDto));
        DeleteCustomerDto deleteCustomerDto = new DeleteCustomerDto("testtest");
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/customers/" + customerId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(deleteCustomerDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}
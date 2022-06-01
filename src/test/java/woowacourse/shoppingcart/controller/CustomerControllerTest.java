package woowacourse.shoppingcart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static woowacourse.fixture.Fixture.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
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

class CustomerControllerTest extends ControllerTest{

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
    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
    }

    @Test
    @DisplayName("이메일, 패스워드, 유저 이름을 받아서 CREATED와 Location 헤더에 리소스 접근 URI를 반환한다.")
    void signUp() throws Exception {
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        when(customerService.signUp(any(SignUpDto.class))).thenReturn(CUSTOMER_ID);

        final MockHttpServletResponse response = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(signUpDto)))
                        .andDo(print())
                        .andReturn()
                        .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader(HttpHeaders.LOCATION)).isEqualTo("/api/customers/"+ CUSTOMER_ID);
    }

    @Test
    @DisplayName("유저 이름을 받아서 기존 유저의 이름을 수정한 뒤 수정된 유저를 반환한다.")
    void updateCustomer() throws Exception {
        when(authService.extractEmail(any(String.class))).thenReturn("test@test.com");
        when(customerService.findCustomerByEmail(any(String.class)))
                .thenReturn(new CustomerDto(CUSTOMER_ID, TEST_EMAIL, TEST_USERNAME));
        when(customerService.updateCustomer(any(Long.class),any(UpdateCustomerDto.class))).thenReturn(new CustomerDto(
                CUSTOMER_ID, TEST_EMAIL,"테스트2"));

        UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto("테스트2");

        final MockHttpServletResponse response = mockMvc.perform(put("/api/customers/" + CUSTOMER_ID)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
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
        final CustomerDto customerDto = new CustomerDto(CUSTOMER_ID, TEST_EMAIL, TEST_USERNAME);
        when(authService.extractEmail(any(String.class))).thenReturn(TEST_EMAIL);
        when(customerService.findCustomerByEmail(any(String.class)))
                .thenReturn(customerDto);
        when(authService.login(any(SignInDto.class))).thenReturn(new TokenResponseDto(accessToken, 1000000L, customerDto));
        final DeleteCustomerDto deleteCustomerDto = new DeleteCustomerDto(TEST_PASSWORD);
        final MockHttpServletResponse response = mockMvc.perform(post("/api/customers/" + CUSTOMER_ID)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(deleteCustomerDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}
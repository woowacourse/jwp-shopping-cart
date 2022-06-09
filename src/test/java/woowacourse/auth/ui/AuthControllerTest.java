package woowacourse.auth.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.SignInDto;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.controller.ControllerTest;
import woowacourse.shoppingcart.dto.response.CustomerDto;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static woowacourse.fixture.Fixture.*;

class AuthControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    static Stream<SignInDto> invalidParams() {
        return Stream.of(null, new SignInDto(TEST_EMAIL, null), new SignInDto(null, TEST_PASSWORD));
    }

    @Test
    @DisplayName("이메일과 패스워드를 받아 로그인한 후 accessToken과 유효시간을 반환한다.")
    void login() throws Exception {
        //given
        final CustomerDto customerDto = new CustomerDto(CUSTOMER_ID, TEST_EMAIL, TEST_USERNAME);
        final SignInDto signInDto = new SignInDto(TEST_EMAIL, TEST_PASSWORD);
        when(authService.login(any()))
                .thenReturn(new TokenResponseDto("testAccessToken", 10800000L, customerDto));

        //when
        final MockHttpServletResponse response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(signInDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        //then
        final TokenResponseDto tokenResponseDto = objectMapper.readValue(response.getContentAsString(), TokenResponseDto.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(tokenResponseDto.getAccessToken()).isNotNull();
        assertThat(tokenResponseDto.getExpirationTime()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("invalidParams")
    @DisplayName("파라미터가 비어있는 경우 예외를 발생시킨다.")
    void login_param(SignInDto invalidParams) throws Exception {
        //when
        final MockHttpServletResponse response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(invalidParams)))
                .andDo(print())
                .andReturn()
                .getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}

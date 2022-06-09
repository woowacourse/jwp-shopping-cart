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

//    @DisplayName("유효한 토큰으로 로그인한 자신의 정보를 요청한다.")
//    @Test
//    void getMe_validToken_200() throws Exception {
//        // given
//        String email = "kun@gmail.com";
//        String nickname = "kun";
//        String password = "1q2w3e4r";
//
//        String accessToken = "test-token";
//
//        given(jwtTokenProvider.validateToken(accessToken))
//                .willReturn(true);
//        given(jwtTokenProvider.getPayload(accessToken))
//                .willReturn(email);
//
//        Customer customer = new Customer(1L, nickname, email, password);
//        given(customerService.getByEmail(email))
//                .willReturn(customer);
//
//        // when, then
//        mockMvc.perform(get("/users/me")
//                        .header(HttpHeaders.AUTHORIZATION,accessToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.ALL))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value(email))
//                .andExpect(jsonPath("$.nickname").value(nickname));
//    }

//    @DisplayName("유효한 토큰으로 회원 정보 수정을 성공한다.")
//    @Test
//    void updateMe() {
//        // given
//        String email = "kun@email.com";
//        String password = "qwerasdf123";
//        CustomerCreationRequest createRequest = new CustomerCreationRequest(email, password, "kun");
//        postUser(createRequest);
//
//        TokenRequest tokenRequest = new TokenRequest(email, password);
//        String accessToken = postLogin(tokenRequest)
//                .extract()
//                .as(TokenResponse.class)
//                .getAccessToken();
//
//        String updatedNickname = "rick";
//        CustomerUpdationRequest updateRequest = new CustomerUpdationRequest(updatedNickname, "qwerasdf321");
//
//        // when
//        ValidatableResponse response = RestAssured
//                .given().log().all()
//                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(updateRequest)
//                .when().put("/users/me")
//                .then().log().all();
//
//        ValidatableResponse updatedResponse = getMe(accessToken);
//
//        // then
//        response.statusCode(HttpStatus.NO_CONTENT.value());
//
//        updatedResponse.body("nickname", equalTo(updatedNickname));
//    }
//
//    @DisplayName("유효한 토큰으로 회원 탈퇴에 성공한다.")
//    @Test
//    void deleteMe() {
//        // given
//        String email = "kun@email.com";
//        String password = "qwerasdf123";
//        CustomerCreationRequest createRequest = new CustomerCreationRequest(email, password, "kun");
//        postUser(createRequest);
//
//        TokenRequest tokenRequest = new TokenRequest(email, password);
//        String accessToken = postLogin(tokenRequest)
//                .extract()
//                .as(TokenResponse.class)
//                .getAccessToken();
//
//        // when
//        ValidatableResponse response = RestAssured
//                .given().log().all()
//                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
//                .when().delete("/users/me")
//                .then().log().all();
//
//        ValidatableResponse loginResponse = postLogin(tokenRequest);
//
//        // then
//        response.statusCode(HttpStatus.NO_CONTENT.value());
//        loginResponse.statusCode(HttpStatus.BAD_REQUEST.value())
//                .body("errorCode", equalTo("1002"))
//                .body("message", equalTo("로그인에 실패했습니다."));
//    }
}

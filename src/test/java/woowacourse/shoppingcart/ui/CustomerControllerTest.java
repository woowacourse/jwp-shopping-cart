package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.config.RestDocsConfig;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DuplicateResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.fixture.TokenFixture.ACCESS_TOKEN;
import static woowacourse.fixture.TokenFixture.BEARER;

@DisplayName("유저 API 문서화")
@AutoConfigureRestDocs
@WebMvcTest(CustomerController.class)
@Import(RestDocsConfig.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("유저 회원가입 문서화")
    @Test
    void createCustomer() throws Exception {
        CustomerRequest request =
                new CustomerRequest("giron", "pas1@A!sword");

        given(customerService.signUp(request)).willReturn(1L);

        ResultActions results = mvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(request)));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("customer-sign-up",
                        requestFields(
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저의 이름"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("Location의 url마지막에 customer의 식별자가 있습니다")
                        )
                ));

    }

    @DisplayName("내 정보 조회 문서화")
    @Test
    void getMe() throws Exception {
        Customer customer = new Customer(1L, "giron", "passwordd1@A");
        CustomerResponse response = new CustomerResponse(customer);

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(customerService.getMeById(any())).willReturn(response);

        ResultActions results = mvc.perform(get("/api/customers/me")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("customer-get-me",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        ),
                        responseFields(
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저의 이름")
                        )
                ));
    }

    @DisplayName("내 정보 업데이트 문서화")
    @Test
    void updateCustomer() throws Exception {
        Customer customer = new Customer(1L, "giron", "paA@14ssdd");

        CustomerResponse response = new CustomerResponse(customer);
        CustomerRequest request =
                new CustomerRequest("giron", "upda12ePa!sD@");

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(customerService.updateById(any(), any())).willReturn(response);

        ResultActions results = mvc.perform(put("/api/customers/me")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(request)));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("customer-update-me",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        ),
                        requestFields(
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저의 이름"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("변경하려는 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저의 이름")
                        )
                ));
    }

    @DisplayName("회원 탈퇴 문서화")
    @Test
    void deleteCustomer() throws Exception {
        Customer customer = new Customer(1L, "giron", "paA@14sswordd");

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        willDoNothing().given(customerService).deleteById(any());

        ResultActions results = mvc.perform(delete("/api/customers/me")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("customer-delete-me",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        )
                ));
    }

    @DisplayName("중복 이름 확인 문서화")
    @Test
    void duplicateUserName() throws Exception {

        DuplicateResponse response = new DuplicateResponse(false);

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(customerService.isDuplicateUserName(any())).willReturn(response);

        ResultActions results = mvc.perform(get("/api/customers/exists")
                .param("userName", "giron")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("customer-duplication-check-name",
                        requestParameters(
                                parameterWithName("userName").description("중복검사 확인 할 유저 이름")
                        ),
                        responseFields(
                                fieldWithPath("isDuplicate").type(JsonFieldType.BOOLEAN).description("중복된다면 true, 아니면 false")
                        )
                ));

    }
}

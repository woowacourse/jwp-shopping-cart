package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.config.AuthenticationPrincipalArgumentResolver;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.EmailValidationRequest;
import woowacourse.shoppingcart.exception.InvalidTokenException;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    @DisplayName("회원 가입에 성공하면 상태 코드 201과 회원 정보, URI 정보를 반환한다.")
    @Test
    void registerCustomer() throws Exception {
        // given
        CustomerRequest customerRequest = new CustomerRequest("test@test.com", "Test1234!@", "yum", "010-0000-0000",
                "서울시 종로구");
        when(customerService.register(any(CustomerRequest.class)))
                .thenReturn(new CustomerResponse(1L, "test@test.com", "yum", "010-0000-0000", "서울시 종로구"));
        // when
        ResultActions perform = mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequest)));
        // then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/customers/login"))
                .andExpect(jsonPath("id").exists());
    }

    @DisplayName("회원 조회 성공 시 상태코드 200과 회원 정보를 반환한다.")
    @Test
    void findCustomer() throws Exception {
        // given
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull()
                , (ModelAndViewContainer) notNull()
                , (NativeWebRequest) notNull()
                , (WebDataBinderFactory) notNull()))
                .thenReturn(1L);
        when(customerService.findCustomerById(any(Long.class)))
                .thenReturn(new CustomerResponse(1L, "test@test.com", "bunny", "010-0000-0000", "서울시 종로구"));
        // when
        ResultActions perform = mockMvc.perform(get("/customers"));
        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists());
    }

    @DisplayName("회원 수정 성공 시 상태코드 200을 반환한다.")
    @Test
    void updateCustomer() throws Exception {
        // given
        CustomerRequest updateCustomerRequest = new CustomerRequest("test@test.com", "Test1234!@", "yum",
                "010-0000-0000",
                "서울시 종로구");
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull()
                , (ModelAndViewContainer) notNull()
                , (NativeWebRequest) notNull()
                , (WebDataBinderFactory) notNull()))
                .thenReturn(1L);
        // when
        ResultActions perform = mockMvc.perform(put("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCustomerRequest)));
        // then
        perform.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원 삭제 성공 시 상태코드 204을 반환한다.")
    @Test
    void deleteCustomer() throws Exception {
        // given
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull()
                , (ModelAndViewContainer) notNull()
                , (NativeWebRequest) notNull()
                , (WebDataBinderFactory) notNull()))
                .thenReturn(1L);
        // when
        ResultActions perform = mockMvc.perform(delete("/customers"));
        // then
        perform.andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("회원 이메일 검증 시 상태 코드 200과 검증 결과를 반환한다.")
    @Test
    void checkEmail() throws Exception {
        // given
        doNothing().when(customerService)
                .validateEmail("test@test.com");
        // when
        ResultActions perform = mockMvc.perform(post("/customers/email/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new EmailValidationRequest("test@test.com"))));
        // then
        perform.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("토큰이 유효하지않을 경우 상태코드 401을 반환한다.")
    @Test
    void findCustomer_InvalidToken() throws Exception {
        // given
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull()
                , (ModelAndViewContainer) notNull()
                , (NativeWebRequest) notNull()
                , (WebDataBinderFactory) notNull()))
                .thenThrow(new InvalidTokenException());
        // when
        ResultActions perform = mockMvc.perform(get("/customers"));
        // then
        perform.andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").exists());
    }

    @DisplayName("회원 이름 조회 성공 시 상태코드 200과 회원 이름을 반환한다.")
    @Test
    void findCustomerName() throws Exception {
        // given
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull()
                , (ModelAndViewContainer) notNull()
                , (NativeWebRequest) notNull()
                , (WebDataBinderFactory) notNull()))
                .thenReturn(1L);
        when(customerService.findCustomerById(any(Long.class)))
                .thenReturn(new CustomerResponse(1L, "test@test.com", "bunny", "010-0000-0000", "서울시 종로구"));
        // when
        ResultActions perform = mockMvc.perform(get("/customers/name"));
        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("bunny"));
    }
}

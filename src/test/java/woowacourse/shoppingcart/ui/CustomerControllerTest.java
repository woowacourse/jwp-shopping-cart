package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.exception.badRequest.EmailDuplicateException;
import woowacourse.exception.unauthorization.PasswordIncorrectException;
import woowacourse.exception.unauthorization.TokenInvalidException;
import woowacourse.exception.notFound.CustomerNotFoundException;
import woowacourse.shoppingcart.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdatePasswordRequest;

public class CustomerControllerTest extends ControllerTest {

    @DisplayName("회원 생성 시도 시, 입력한 이메일이 이미 존재할 경우 400을 응답한다")
    @Test
    void createCustomerWithAlreadyExistEmailShouldFail() throws Exception {
        doThrow(new EmailDuplicateException()).when(customerService).create(any(CustomerRequest.class));

        // when then
        mockMvc.perform(postWithBody("/api/customer", 잉_회원생성요청)).andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("회원 생성 시도 시, 입력된 비밀번호가 8자 미만이면 400을 응답한다")
    @Test
    void createCustomerWithPasswordLengthLessThanEightShouldFail() throws Exception {
        // given
        doThrow(new EmailDuplicateException()).when(customerService).create(any(CustomerRequest.class));

        // when then
        mockMvc.perform(postWithBody("/api/customer", 잉_회원생성요청)).andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("회원 생성 시도 시 비밀번호가 비어있으면 400을 응답한다.")
    @Test
    void createCustomerWithBlankPasswordShouldFail() throws Exception {
        // given
        final Map<String, String> params = Map.of("email", "ing@woowahan.com", "name", "ing", "password", "");

        // when then
        mockMvc.perform(postWithBody("/api/customer", params)).andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("회원 생성 시도 시 이메일이 비어있으면 400을 응답한다")
    @Test
    void createCustomerWithBlankEmailShouldFail() throws Exception {
        // given
        final Map<String, String> params = Map.of("email", "", "name", "ing", "password", "ing_password");

        // when then
        mockMvc.perform(postWithBody("/api/customer", params)).andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("회원 생성 시도 시 이메일 형식이 유효하지 않을 경우 400을 응답한다")
    @Test
    void createCustomerWithInvalidEmailShouldFail() throws Exception {
        // given
        final CustomerRequest customerRequest = new CustomerRequest("리차드", "email@email.", "12345678");

        // when then
        mockMvc.perform(postWithBody("/api/customer", customerRequest)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 생성 시도 시 이름 비어있으면 400을 응답한다")
    @Test
    void createCustomerWithBlankNameShouldFail() throws Exception {
        // given
        final Map<String, String> params = Map.of("email", "ing@woowahan.com", "name", "", "password", "ing_password");

        // when then
        mockMvc.perform(postWithBody("/api/customer", params)).andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("유효하지 않은 토큰으로 회원 조회 시도시 응답코드로 401을 응답한다")
    @Test
    void findCustomerWithInvalidTokenShouldFail() throws Exception {
        // given
        final String invalidToken = "some.invalid.token";
        when(jwtTokenProvider.getPayload(invalidToken)).thenThrow(new TokenInvalidException());

        // when then
        mockMvc.perform(getWithToken("/api/customer", invalidToken)).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("토큰은 유효하지만 해당하는 회원이 존재하지 않을 경우 응답코드로 400을 응답한다")
    @Test
    void findNotExistCustomerShouldFail() throws Exception {
        // given
        final String token = TOKEN;

        when(jwtTokenProvider.getPayload(token)).thenReturn(101L);
        when(customerService.findById(101L)).thenThrow(new CustomerNotFoundException());

        // when then
        mockMvc.perform(getWithToken("/api/customer", token))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("회원 이름 변경 시도 시, 이름이 비어있으면 400을 응답한다")
    @Test
    void updateCustomerNameWithBlankNameShouldFail() throws Exception {
        // given
        final Map<String, String> params = Map.of("name", "");

        // when then
        mockMvc.perform(updateWithToken("/api/customer/profile", TOKEN, params))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호 변경 시도 시, 새로운 비밀번호 길이가 8자 미만인 경우 400을 응답한다")
    @Test
    void updateCustomerPasswordWithNewPasswordLengthLessThanEightShouldFail() throws Exception {
        // given
        final CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest(
                "some_random_password", "1234567");

        // when then
        mockMvc.perform(updateWithToken("/api/customer/password", TOKEN, customerUpdatePasswordRequest)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호 변경 시도 시, 기존 비밀번호 길이가 8자 미만인 경우 400을 응답한다")
    @Test
    void updateCustomerPasswordWithOldPasswordLengthLessThanEightShouldFail() throws Exception {
        // given
        final CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234567",
                "12345678");

        // when then
        mockMvc.perform(updateWithToken("/api/customer/password", TOKEN, customerUpdatePasswordRequest)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호 변경 시도 시, 기존 비밀번호가 일치하지 않는 경우 400을 응답한다")
    @Test
    void updateCustomerPasswordWithInvalidOldPasswordShouldFail() throws Exception {
        // given
        final CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest(
                "some_random_password", "some_new_password");

        doThrow(new PasswordIncorrectException()).when(customerService)
                .updatePassword(any(Long.class), any(CustomerUpdatePasswordRequest.class));

        // when then
        mockMvc.perform(updateWithToken("/api/customer/password", TOKEN, customerUpdatePasswordRequest)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호 변경 시도 시, 새 비밀번호가 비어있는 경우 400을 응답한다")
    @Test
    void updateCustomerPasswordWithBlankNewPasswordShouldFail() throws Exception {
        // given
        final CustomerUpdatePasswordRequest invalidPasswordUpdateRequest = new CustomerUpdatePasswordRequest("12345678",
                "");

        // when then
        mockMvc.perform(updateWithToken("/api/customer/password", TOKEN, invalidPasswordUpdateRequest)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호 변경 시도 시, 이전 비밀번호가 비어있는 경우 400을 응답한다")
    @Test
    void updateCustomerPasswordWithBlankOldPasswordShouldFail() throws Exception {
        // given
        final CustomerUpdatePasswordRequest invalidPasswordUpdateRequest = new CustomerUpdatePasswordRequest("",
                "12345678");

        // when then
        mockMvc.perform(updateWithToken("/api/customer/password", TOKEN, invalidPasswordUpdateRequest)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 탈퇴 시도 시 입력한 비밀번호가 일치하지 않는 경우 400을 응답한다")
    @Test
    void deleteCustomerWithInvalidPasswordShouldFail() throws Exception {
        // given
        final Map<String, String> params = Map.of("email", "ing@woowahan.com", "name", "ing", "password",
                "some_random_password");

        doThrow(new PasswordIncorrectException()).when(customerService)
                .delete(any(Long.class), any(CustomerDeleteRequest.class));

        // when then
        mockMvc.perform(deleteWithToken("/api/customer", TOKEN, params)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 탈퇴 시도 시 입력한 비밀번호가 8자 미만일 경우 400을 응답한다")
    @Test
    void deleteCustomerWithPasswordLengthLessThanEightShouldFail() throws Exception {
        // given
        final Map<String, String> params = Map.of("email", "ing@woowahan.com", "name", "ing", "password", "1234567");

        // when then
        mockMvc.perform(deleteWithToken("/api/customer", TOKEN, params)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 탈퇴 시도 시 비밀번호가 비어있는 경우 400을 응답한다")
    @Test
    void deleteCustomerWithBlankPasswordShouldFail() throws Exception {
        // given
        final Map<String, String> params = Map.of("email", "ing@woowahan.com", "name", "ing", "password", "");

        // when then
        mockMvc.perform(postWithBody("/api/customer", params)).andDo(print()).andExpect(status().isBadRequest());
    }
}

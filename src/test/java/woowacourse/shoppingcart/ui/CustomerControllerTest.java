package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@DisplayName("CustomerController 단위 테스트")
class CustomerControllerTest extends ControllerTest {

    @Test
    @DisplayName("유효한 양식으로 회원가입에 성공한다.")
    void create_validForm_204() throws Exception {
        // given
        final CustomerCreationRequest request = new CustomerCreationRequest(
                "email@email.com",
                "1q2w3e4r",
                "rick"
        );
        final String json = objectMapper.writeValueAsString(request);

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNoContent());
    }

    @ParameterizedTest(name = "잘못된 {3}으로 회원가입을 요청하면 400을 반환한다.")
    @CsvSource(value = {
            "email#email.com:1q2w3e4r:rick:이메일 양식",
            "email@email.com:1q2w3e:rick:비밀번호 양식",
            "email@email.com:12345678:rick:비밀번호 양식",
            "email@email.com:1q2w3e4r:릭:닉네임 양식"
    }, delimiter = ':')
    void create_invalidForm_400(final String email, final String password, final String nickname, final String message)
            throws Exception {
        // given
        final CustomerCreationRequest request = new CustomerCreationRequest(
                email,
                password,
                nickname
        );
        final String json = objectMapper.writeValueAsString(request);

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1000"))
                .andExpect(jsonPath("message").value(message + "이 잘못 되었습니다."));
    }
}
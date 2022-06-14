package woowacourse.auth.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthCheckInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("토큰_정보가_올바르지_않은_경우_unauthorized 상태코드 반환")
    @Test
    void authCheckInterceptor_test() throws Exception {
        mockMvc.perform(get("/customers/me")
                        .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbklkIiwiaWF0IjoxNjU1MTkzNjk2LCJleHAiOjE2NTUxOTcyOTZ9.b7iqjwiFpmKg1U5e2N81yftkDNTGg-1oDDqyirF8CEinvalid"))
                .andExpect(status().isUnauthorized());
    }
}

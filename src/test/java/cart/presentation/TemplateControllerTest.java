package cart.presentation;

import cart.business.MemberService;
import cart.business.ProductCRUDService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TemplateController.class)
class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductCRUDService productCRUDApplication;
    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("/ 로 GET 요청을 보낼 수 있다")
    void test_home() throws Exception {
        // given
        given(productCRUDApplication.readAll()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/"))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/admin 으로 GET 요청을 보낼 수 있다")
    void test_admin() throws Exception {
        // given
        given(productCRUDApplication.readAll()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/admin"))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/settings 으로 GET 요청을 보낼 수 있다")
    void test_settings() throws Exception {
        // given
        given(memberService.readAll()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/settings"))

                // then
                .andExpect(status().isOk());
    }
}

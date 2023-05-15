package cart.controller.view;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.dto.MemberDto;
import cart.service.MemberService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SettingsPageController.class)
class SettingsPageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("설정 페이지를 보여준다.")
    void settingPage() throws Exception {
        // given
        List<MemberDto> allMembers = List.of(
                MemberDto.builder()
                        .id(1L)
                        .email("joy@gmail.com")
                        .password("12345678")
                        .build());

        willReturn(allMembers)
                .given(memberService)
                .findAll();

        // expected
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("members", allMembers))
                .andExpect(view().name("settings"));
    }
}

package cart.controller.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.domain.member.Member;
import cart.dto.MembersResponse;
import cart.service.MemberService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(SettingController.class)
class SettingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @DisplayName("GET /settings")
    @Test
    void getSettings() throws Exception {
        List<Member> members = List.of(
                new Member((long) 1, "a@a.com", "abc1", "이오"),
                new Member((long) 2, "b@b.com", "abc2", "애쉬")
        );
        given(memberService.findAll()).willReturn(members);

        MvcResult result = mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(view().name("settings"))
                .andReturn();

        Object response = result.getModelAndView().getModel().get("members");
        assertThat(response)
                .isInstanceOf(MembersResponse.class)
                .usingRecursiveComparison()
                .isEqualTo(MembersResponse.of(members));
    }
}

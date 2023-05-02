package cart.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
                new Member("a@a.com", "abc1", "이오"),
                new Member("b@b.com", "abc2", "애쉬")
        );
        given(memberService.findAll()).willReturn(members);
        MembersResponse response = MembersResponse.of(members);

        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(view().name("settings"))
                .andExpect(model().attribute("members", instanceOf(MembersResponse.class)))
                .andExpect(model().attribute("members", hasProperty("members",
                        allOf(
                                hasItem(
                                        allOf(
                                                hasProperty("email", is("a@a.com")),
                                                hasProperty("password", is("abc1")),
                                                hasProperty("name", is("이오"))
                                        )
                                ),
                                hasItem(
                                        allOf(
                                                hasProperty("email", is("b@b.com")),
                                                hasProperty("password", is("abc2")),
                                                hasProperty("name", is("애쉬"))
                                        )
                                )
                        )
                )));
    }
}

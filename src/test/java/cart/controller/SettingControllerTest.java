package cart.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.controller.dto.response.MemberResponse;
import cart.service.MemberService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SettingController.class)
class SettingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @DisplayName("멤버 목록을 보여준다 settings.html")
    @Test
    public void showProductsList() throws Exception {
        final MemberResponse 우가 = new MemberResponse(1, "우가", "1234");
        final MemberResponse 로이 = new MemberResponse(2, "로이", "1234");
        final MemberResponse 제이미 = new MemberResponse(3, "제이미", "1234");
        final List<MemberResponse> list = List.of(우가, 로이, 제이미);
        given(memberService.findAllMemberId()).willReturn(list);

        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("settings"))
                .andExpect(model().attribute("members", list));
    }

}

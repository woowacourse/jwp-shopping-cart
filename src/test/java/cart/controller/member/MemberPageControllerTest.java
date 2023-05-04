package cart.controller.member;

import cart.dto.MemberDto;
import cart.service.MemberService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(MemberPageController.class)
public class MemberPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    void 회원가입_페이지를_조회한다() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    void 로그인_페이지를_조회한다() throws Exception {
        List<MemberDto> members = List.of(
                new MemberDto(1L, "ehdgur4814@naver.com", "hardy", "1234"),
                new MemberDto(2L, "ehdgur4814@google.com", "hardy", "1234"));
        given(memberService.findAllMember())
                .willReturn(members);

        mockMvc.perform(get("/settings"))
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attribute("members", members))
                .andExpect(status().isOk());
    }
}

package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.helper.ControllerTestHelper;
import cart.service.MemberService;
import cart.service.dto.MemberResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends ControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("사용자 리스트를 조회한다.")
    void getMembers() throws Exception {
        // given
        final MemberResponse journey = new MemberResponse(1L, "USER", "journey@gmail.com",
            "password", "져니", "010-1234-5678");
        final MemberResponse koda = new MemberResponse(2L, "USER", "koda@gmail.com",
            "test1234", "코다", "010-4321-8765");
        final List<MemberResponse> memberResponses = List.of(journey, koda);
        when(memberService.getMembers()).thenReturn(memberResponses);

        // when, then
        mockMvc.perform(get("/settings")
                .contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자 정보를 조회한다")
    void getMember() throws Exception {
        // given
        final MemberResponse journey = new MemberResponse(1L, "USER", "journey@gmail.com",
            "password", "져니", "010-1234-5678");
        when(memberService.getById(any())).thenReturn(journey);

        // when, then
        mockMvc.perform(get("/member/{memberId}", 1L)
                .contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }
}

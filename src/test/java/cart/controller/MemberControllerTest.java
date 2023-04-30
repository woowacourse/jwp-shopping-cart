package cart.controller;

import cart.controller.dto.MemberDto;
import cart.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("사용자 리스트를 조회한다.")
    void getMembers() throws Exception {
        // given
        final MemberDto journey = new MemberDto(1L, "journey@gmail.com", "password", "져니", "010-1234-5678");
        final MemberDto koda = new MemberDto(2L, "koda@gmail.com", "test1234", "코다", "010-4321-8765");
        final List<MemberDto> memberDtos = List.of(journey, koda);
        when(memberService.getMembers()).thenReturn(memberDtos);

        // when, then
        mockMvc.perform(get("/settings")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }
}

package cart.controller.member;

import cart.dto.MemberDto;
import cart.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    void 회원가입을_한다() throws Exception {
        final String email = "ehdgur4814@naver.com";
        final String name = "hardy";
        final String password = "1234";
        final MemberDto member = new MemberDto(email, name, password);
        final String requestBody = objectMapper.writeValueAsString(member);
        given(memberService.registerMember(member))
                .willReturn(anyLong());

        mockMvc.perform(post("/register")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void 로그인을_한다() throws Exception {
        final String email = "ehdgur4814@naver.com";
        final String name = "hardy";
        final String password = "1234";
        final MemberDto member = new MemberDto(email, name, password);
        final String requestBody = objectMapper.writeValueAsString(member);
        given(memberService.loginMember(anyString(), anyString()))
                .willReturn(member);

        mockMvc.perform(post("/settings")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}

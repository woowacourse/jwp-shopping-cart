package cart.controller;

import cart.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.fixture.MemberFixture.FIRST_MEMBER;
import static cart.fixture.MemberFixture.SECOND_MEMBER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 사용자를_조회한다() throws Exception {
        given(memberService.findAll()).willReturn(List.of(FIRST_MEMBER.RESPONSE, SECOND_MEMBER.RESPONSE));

        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void 사용자를_생성한다() throws Exception {
        mockMvc.perform(post("/members")
                        .content(objectMapper.writeValueAsString(FIRST_MEMBER.REQUEST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}

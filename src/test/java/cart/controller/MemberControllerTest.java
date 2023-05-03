package cart.controller;

import cart.dao.MemberDaoImpl;
import cart.dto.request.MemberRequest;
import cart.fixture.MemberFixture;
import cart.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(MemberController.class)
@Import({MemberDaoImpl.class})
@MockBean(JdbcTemplate.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    void 사용자_회원가입_요청() throws Exception {
        MemberRequest request = MemberFixture.JUNO.REQUEST;
        String jsonRequest = objectMapper.writeValueAsString(request);

        when(memberService.create(any()))
                .thenReturn(MemberFixture.JUNO.RESPONSE);

        mockMvc.perform(post("/members")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void 회원정보_수정_요청() throws Exception {
        MemberRequest request = MemberFixture.JUNO.REQUEST;
        String jsonRequest = objectMapper.writeValueAsString(request);

        when(memberService.update(any(), any()))
                .thenReturn(MemberFixture.JUNO.RESPONSE);

        mockMvc.perform(put("/members/{id}", 1L)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 회원_탈퇴_요청() throws Exception {
        mockMvc.perform(delete("/members/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

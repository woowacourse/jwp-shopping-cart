package cart.controller;

import cart.dao.MemberDao;
import cart.domain.user.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberDao memberDao;

    @DisplayName("유저 정보를 모두 조회한다.")
    @Test
    void membersTest() throws Exception {
        given(memberDao.findAll())
                .willReturn(List.of(new Member("user1@email.com", "1234")));

        final MvcResult mvcResult = mockMvc.perform(get("/settings")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();

        final String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).contains("user1@email.com");
    }
}

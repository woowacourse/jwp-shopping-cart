package cart.controller.member;

import cart.service.member.Member;
import cart.service.member.MemberDao;
import cart.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberDao memberDao;

    @MockBean
    MemberService memberService;

    @Test
    void 관리자_페이지_접근() throws Exception {
        memberDao.save(new Member("dudgh@gmail.com", "qwer1234"));

        mockMvc.perform(get("/settings"))
                .andExpect(view().name("settings"))
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(status().isOk());
    }
}

package cart.controller.view;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.auth.AuthContext;
import cart.dao.MemberDao;
import cart.domain.Member;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("SettingController 는")
@WebMvcTest(SettingController.class)
@MockBean({MemberDao.class, AuthContext.class})
class SettingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberDao memberDao;

    @Test
    void 회원목록을_보여준다() throws Exception {
        final Member 말랑 = new Member("mallang@mallang.com", "mallang123");
        final Member 완태 = new Member("wannte@wannte.com", "wannte123");
        final List<Member> members = List.of(말랑, 완태);

        given(memberDao.findAll()).willReturn(members);
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("settings"))
                .andExpect(model().attribute("members", members));
    }
}

package cart.controller;

import cart.authentication.controller.MemberController;
import cart.authentication.entity.Member;
import cart.authentication.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("/setting으로 접근하면 등록된 멤버 목록을 보여준다.")
    void findAll() throws Exception {
        List<Member> members = List.of(
                new Member("test1@gmail.com", "password1234!"),
                new Member("test1@gmail.com", "password1234!")
        );

        Mockito.when(memberRepository.findAll())
                .thenReturn(members);

        mockMvc.perform(get("/settings"))
                .andExpect(model().attribute("members", members));
    }
}
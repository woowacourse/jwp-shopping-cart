package cart.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.domain.Member;
import cart.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;

@SpringBootTest
@MockitoSettings
class MemberServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    void 모든_회원_조회() {
        Mockito.when(memberRepository.findAll())
                .thenReturn(List.of(
                        new Member("email", "password", "name", "address", 1),
                        new Member("email", "password", "name", "address", 2),
                        new Member("email", "password", "name", "address", 3)
                ));

        final List<Member> result = memberService.findAll();

        assertThat(result.size()).isEqualTo(3);
    }
}

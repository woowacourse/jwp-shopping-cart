package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.Member;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("멤버 리스트를 조회한다")
    void findAll() {
        List<Member> members = memberService.findAll();

        assertThat(members).extracting("name")
                .containsExactly("이오", "애쉬");
    }
}

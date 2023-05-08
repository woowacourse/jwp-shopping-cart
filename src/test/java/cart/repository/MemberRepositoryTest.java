package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Member;
import cart.service.dto.MemberInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcMemberRepository.class)
class MemberRepositoryTest {

    @Autowired
    private JdbcMemberRepository memberRepository;

    @Test
    @DisplayName("repository에 저장된 모든 Member를 반환하는 기능 테스트")
    void findAllTest() {
        final List<Member> all = memberRepository.findAll();

        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("이메일과 패스워드로 id를 찾는 기능 테스트")
    void findId() {
        final MemberInfo memberInfo = new MemberInfo("hongSile@wooteco.com", "hongSile");
        final Long memberId = memberRepository.findId(memberInfo)
                .orElseThrow(IllegalArgumentException::new);

        assertThat(memberId).isEqualTo(1L);
    }
}

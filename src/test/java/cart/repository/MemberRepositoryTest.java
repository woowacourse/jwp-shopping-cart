package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Member;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(MemberRepository.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("repository에 저장된 모든 Member를 반환하는 기능 테스트")
    void findAllTest() {
        final List<Member> all = memberRepository.findAll();

        assertThat(all).hasSize(2);
    }
}

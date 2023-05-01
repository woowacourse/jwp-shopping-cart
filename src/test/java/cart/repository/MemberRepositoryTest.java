package cart.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(value = {"/schema.sql", "/data.sql"})
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 모든_회원을_조회한다() {
        final var members = memberRepository.findAll();

        assertAll(
                () -> assertThat(members.size()).isEqualTo(2),
                () -> assertThat(members.get(0)).isInstanceOf(Member.class)
        );
    }
}

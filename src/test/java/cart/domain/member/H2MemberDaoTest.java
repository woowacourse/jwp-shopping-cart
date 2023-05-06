package cart.domain.member;

import static cart.fixture.MemberFixture.MEMBER1;
import static cart.fixture.MemberFixture.MEMBER2;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class H2MemberDaoTest {

    private final MemberDao memberDao;

    @Autowired
    public H2MemberDaoTest(final JdbcTemplate jdbcTemplate) {
        memberDao = new H2MemberDao(jdbcTemplate);
    }

    @DisplayName("모든 member를 조회한다")
    @Test
    void findAll() {
        // TODO 저장하는 멤버 데이터 상수로 관리하기
        assertThat(memberDao.findAll())
                .containsOnly(MEMBER1, MEMBER2);
    }

    @DisplayName("해당 이메일을 가진 member를 조회한다")
    @Test
    void findByEmail() {
        assertThat(memberDao.findByEmail(MEMBER1.getEmail()).get())
                .isEqualTo(MEMBER1);
    }
}

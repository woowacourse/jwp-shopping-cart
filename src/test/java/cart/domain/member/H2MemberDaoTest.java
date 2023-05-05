package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.Dao;
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

    private Dao<Member> memberDao;

    @Autowired
    public H2MemberDaoTest(final JdbcTemplate jdbcTemplate) {
        memberDao = new H2MemberDao(jdbcTemplate);
    }

    @DisplayName("모든 member를 조회한다")
    @Test
    void findAll() {
        // TODO 저장하는 멤버 데이터 상수로 관리하기
        assertThat(memberDao.findAll())
                .containsOnly(
                        new Member(1L, new Email("dummy@gmail.com"), new Password("abcd1234")),
                        new Member(2L, new Email("dummy2@gmail.com"), new Password("abcd5678"))
                );
    }
}

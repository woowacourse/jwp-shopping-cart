package cart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@JdbcTest
class MemberDaoTest {

    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void 이메일_비밀번호로_회원아이디를_찾는다() {
        final String email1 = "a@a.com";
        final String password1 = "password1";
        Long id1 = memberDao.findIdByAuthInfo(email1, password1);

        final String email2 = "b@b.com";
        final String password2 = "password2";
        Long id2 = memberDao.findIdByAuthInfo(email2, password2);

        assertSoftly(softly -> {
            softly.assertThat(id1).isNotNull();
            softly.assertThat(id2).isNotNull();
        });
    }
}

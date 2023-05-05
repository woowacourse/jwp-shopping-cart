package cart.dao;

import cart.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
public class MemberDaoTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
        memberDao = new MemberDao(namedParameterJdbcTemplate);
        memberDao.deleteAll();
    }

    @Test
    void 모든_사용자를_조회한다() {
        final Member firstMember = new Member("hongo@wooteco.com", "hongo_password");
        final Member secondMember = new Member("turtle@wooteco.com", "turtle_password");
        memberDao.save(firstMember);
        memberDao.save(secondMember);

        final Member expectedFirstMember = new Member(1L, "hongo@wooteco.com", "hongo_password");
        final Member expectedSecondMember = new Member(2L, "turtle@wooteco.com", "turtle_password");

        assertThat(memberDao.findAll()).containsExactly(expectedFirstMember, expectedSecondMember);
    }

    @Test
    void 사용자를_생성한다() {
        final Member member = new Member("hongo@wooteco.com", "hongo_password");
        memberDao.save(member);

        final Member expectedMember = new Member(1L, "hongo@wooteco.com", "hongo_password");
        final Member actualMember = memberDao.findByEmail("hongo@wooteco.com");
        assertThat(actualMember).isEqualTo(expectedMember);
    }
}

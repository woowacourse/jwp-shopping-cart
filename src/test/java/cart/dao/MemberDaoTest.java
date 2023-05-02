package cart.dao;

import cart.domain.user.Member;
import cart.persistance.dao.MemberDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class MemberDaoTest {

    private final MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDaoTest(final JdbcTemplate jdbcTemplate) {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("유저를 전부 조회한다.")
    @Test
    void findAllTest() {
        final List<Member> allMembers = memberDao.findAll();

        assertThat(allMembers).hasSize(2);
    }

    @DisplayName("email로 멤버를 조회한다")
    @Test
    void findByEmailTest() {
        jdbcTemplate.update("INSERT INTO member (email, password) " +
                "VALUES ('aaa@woowa.com', '1234')");
        final Member member = memberDao.findByEmail("aaa@woowa.com");

        assertThat(member.getPassword()).isEqualTo("1234");
    }
}

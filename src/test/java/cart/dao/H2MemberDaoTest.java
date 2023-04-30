package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class H2MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new H2MemberDao(jdbcTemplate);
    }

    @DisplayName("회원을 저장한다")
    @Test
    void saveMember() {
        Member member = new Member("boxster@email.com", "boxster");

        Member savedMember = memberDao.save(member);

        assertThat(memberDao.findById(savedMember.getId())).isNotEmpty();
    }

    @DisplayName("전체 회원을 조회한다")
    @Test
    void findAllMember() {
        memberDao.save(new Member("boxster@email.com", "boxster"));
        memberDao.save(new Member("gitchan@email.com", "gitchan"));

        assertThat(memberDao.findAll()).hasSize(2);
    }
}

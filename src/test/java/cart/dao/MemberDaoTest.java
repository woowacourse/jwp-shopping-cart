package cart.dao;

import cart.domain.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("MemberDao 은")
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void 맴버_저장() {
        // when
        final Long id = memberDao.save(new Member("chae@email.com", "aaaa"));

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 맴버_모두_조회() {
        // when
        memberDao.save(new Member("chae@email.com", "aaaa"));
        memberDao.save(new Member("chae2@email.com", "aaaa2"));

        assertThat(memberDao.findAll()).hasSize(2);
    }
}

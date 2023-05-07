package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.Email;
import cart.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@JdbcTest
class MemberJdbcDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberJdbcDao memberJdbcDao;

    @BeforeEach
    void setUp() {
        memberJdbcDao = new MemberJdbcDao(jdbcTemplate);
    }

    @Test
    void 모든_유저를_조회한다() {
        assertThat(memberJdbcDao.findAll()).hasSize(2);
    }

    @Test
    void 이메일로_유저를_조회한다() {
        // given
        final Email email = new Email("blackcat@teco.com");

        // when
        final Member member = memberJdbcDao.findByEmail(email).get();

        //then
        assertThat(member.getEmail().email()).isEqualTo(email.email());
    }
}

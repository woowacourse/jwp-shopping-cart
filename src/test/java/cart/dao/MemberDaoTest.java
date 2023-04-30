package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("MemberDao 는")
@JdbcTest
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void 회원을_이메일로_조회한다() {
        // given
        memberDao.save(new Member("mallang@mallang.com", "mallang123"));

        // expected
        assertThat(memberDao.findByEmail("mallang@mallang.com")).isPresent();
    }

    @Test
    void 회원을_이메일로_조회_실패() {
        // expected
        assertThat(memberDao.findByEmail("mallang@mallang.com"))
                .isEmpty();
    }

    @Test
    void 회원_전체_조회() {
        // given
        memberDao.save(new Member("mallang@mallang.com", "mallang123"));
        memberDao.save(new Member("wannte@wannte.com", "wannte123"));

        // expected
        assertThat(memberDao.findAll()).hasSize(2);
    }
}

package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.domain.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class MemberDaoTest {
    public static final Member MEMBER_FIXTURE = new Member("gavi@wooteco.com", "1234");

    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void 이메일로_회원이_존재하는지_확인할_수_있다() {
        // given
        jdbcTemplate.execute("INSERT INTO MEMBER(email, password) VALUES ('gavi@wooteco.com', '1234')");

        // when
        final Optional<Member> MemberOptional = memberDao.findByEmail(MEMBER_FIXTURE.getEmail());

        // then
        assertSoftly(softly -> {
            Member memberEntity = MemberOptional.get();
            softly.assertThat(memberEntity.getEmail()).isEqualTo("gavi@wooteco.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234");
        });
    }

    @Test
    void 이메일과_비밀번호로_회원이_존재하는지_확인할_수_있다() {
        // given
        jdbcTemplate.execute("INSERT INTO MEMBER(email, password) VALUES ('gavi@wooteco.com', '1234')");

        // when
        final Optional<Member> MemberOptional = memberDao.findByEmailAndPassword(MEMBER_FIXTURE.getEmail(), MEMBER_FIXTURE.getPassword());

        // then
        assertSoftly(softly -> {
            Member memberEntity = MemberOptional.get();
            softly.assertThat(memberEntity.getEmail()).isEqualTo("gavi@wooteco.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234");
        });
    }
}

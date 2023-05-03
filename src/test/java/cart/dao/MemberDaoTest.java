package cart.dao;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.entity.MemberEntity;
import cart.domain.Member;
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

    private static final Member MEMBER_FIXTURE = new Member("gavi@wooteco.com", "1234");

    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void 회원을_추가할_수_있다() {
        // when
        int insertedMember = memberDao.insert(MEMBER_FIXTURE);

        // then
        assertThat(insertedMember).isOne();
    }

    @Test
    void 회원이_존재하는지_확인할_수_있다() {
        // given
        memberDao.insert(MEMBER_FIXTURE);

        // when
        final Optional<MemberEntity> memberEntityOptional = memberDao.findMember(MEMBER_FIXTURE);

        // then
        assertSoftly(softly -> {
            MemberEntity memberEntity = memberEntityOptional.get();
            softly.assertThat(memberEntity.getEmail()).isEqualTo("gavi@wooteco.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234");
        });
    }

    @Test
    void 특정_이메일을_가진_회원을_조회할_수_있다() {
        // given
        memberDao.insert(MEMBER_FIXTURE);

        // when
        final Optional<MemberEntity> memberEntityOptional = memberDao.findMemberByEmail(MEMBER_FIXTURE.getEmail());

        // then
        assertSoftly(softly -> {
            MemberEntity memberEntity = memberEntityOptional.get();
            softly.assertThat(memberEntity.getEmail()).isEqualTo("gavi@wooteco.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234");
        });
    }
}

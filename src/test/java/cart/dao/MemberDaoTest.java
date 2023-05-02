package cart.dao;

import cart.dao.entity.MemberEntity;
import cart.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
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
    void 모든_데이터를_조회한다() {
        //given
        memberDao.insert(new Member("huchu@woowahan.com", "1234567a!"));

        //when
        final List<MemberEntity> memberEntities = memberDao.findAll();

        //then
        assertSoftly(softly -> {
            softly.assertThat(memberEntities).hasSize(1);
            final MemberEntity memberEntity = memberEntities.get(0);
            softly.assertThat(memberEntity.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234567a!");
        });
    }

    @Test
    void 데이터를_추가한다() {
        //given
        final Member member = new Member("huchu@woowahan.com", "1234567a!");

        //when
        final Long id = memberDao.insert(member);

        //then
        assertSoftly(softly -> {
            softly.assertThat(id).isNotNull();
            final MemberEntity memberEntity = memberDao.findById(id);
            softly.assertThat(memberEntity.getId()).isEqualTo(id);
            softly.assertThat(memberEntity.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234567a!");
        });
    }

    @Test
    void id로_데이터를_찾는다() {
        //given
        final Member member = new Member("huchu@woowahan.com", "1234567a!");
        final Long id = memberDao.insert(member);

        //when
        final MemberEntity memberEntity = memberDao.findById(id);

        //then
        assertSoftly(softly -> {
            softly.assertThat(memberEntity.getId()).isEqualTo(id);
            softly.assertThat(memberEntity.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234567a!");
        });
    }

    @Test
    void 모든_데이터를_삭제한다() {
        //given
        memberDao.insert(new Member("huchu@woowahan.com", "1234567a!"));

        //when
        memberDao.deleteAll();

        //then
        assertThat(memberDao.findAll()).hasSize(0);
    }

    @Test
    void 이메일로_데이터를_찾는다() {
        //given
        final Member member = new Member("huchu@woowahan.com", "1234567a!");
        final Long id = memberDao.insert(member);

        //when
        final MemberEntity memberEntity = memberDao.findByEmail("huchu@woowahan.com");

        //then
        assertSoftly(softly -> {
            softly.assertThat(memberEntity.getId()).isEqualTo(id);
            softly.assertThat(memberEntity.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234567a!");
        });
    }
}

package cart.dao.member;

import cart.domain.member.Member;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static cart.DummyData.DUMMY_MEMBER_ONE;
import static cart.DummyData.INITIAL_MEMBER_ONE;
import static cart.DummyData.INITIAL_MEMBER_TWO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Import(JdbcMemberDao.class)
@Sql("/reset-member-data.sql")
@JdbcTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JdbcMemberDaoTest {

    @Autowired
    JdbcMemberDao memberDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void 멤버_데이터를_추가할_수_있는지_확인한다() {
        assertDoesNotThrow(() -> memberDao.insert(DUMMY_MEMBER_ONE));
    }

    @Test
    void 멤버_이메일과_비밀번호로_멤버를_반환하는지_확인한다() {
        final Member member = memberDao.findByEmailAndPassword(
                INITIAL_MEMBER_ONE.getEmail(),
                INITIAL_MEMBER_ONE.getPassword()
        );

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(member.getId()).isEqualTo(INITIAL_MEMBER_ONE.getId());
            softAssertions.assertThat(member.getEmail()).isEqualTo(INITIAL_MEMBER_ONE.getEmail());
            softAssertions.assertThat(member.getPassword()).isEqualTo(INITIAL_MEMBER_ONE.getPassword());
        });
    }

    @Test
    void 모든_멤버_데이터를_반환하는지_확인한다() {
        final List<Member> members = memberDao.findAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(members.size()).isEqualTo(2);
            softAssertions.assertThat(members.get(0).getId()).isEqualTo(INITIAL_MEMBER_ONE.getId().intValue());
            softAssertions.assertThat(members.get(0).getEmail()).isEqualTo(INITIAL_MEMBER_ONE.getEmail());
            softAssertions.assertThat(members.get(0).getPassword()).isEqualTo(INITIAL_MEMBER_ONE.getPassword());
            softAssertions.assertThat(members.get(1).getId()).isEqualTo(INITIAL_MEMBER_TWO.getId().intValue());
            softAssertions.assertThat(members.get(1).getEmail()).isEqualTo(INITIAL_MEMBER_TWO.getEmail());
            softAssertions.assertThat(members.get(1).getPassword()).isEqualTo(INITIAL_MEMBER_TWO.getPassword());
        });
    }
}

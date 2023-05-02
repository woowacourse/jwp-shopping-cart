package cart.dao.member;

import cart.domain.member.Member;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Import(JdbcMemberDao.class)
@JdbcTest
class JdbcMemberDaoTest {

    @Autowired
    JdbcMemberDao memberDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        resetTable();
    }

    @DisplayName("모든 멤버 데이터를 반환하는지 확인한다")
    @Test
    void selectAllTest() {
        final List<Member> members = memberDao.selectAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(members.size()).isEqualTo(2);
            softAssertions.assertThat(members.get(0).getId()).isEqualTo(1L);
            softAssertions.assertThat(members.get(0).getEmail()).isEqualTo("test@test.com");
            softAssertions.assertThat(members.get(0).getPassword()).isEqualTo("test");
            softAssertions.assertThat(members.get(1).getId()).isEqualTo(2L);
            softAssertions.assertThat(members.get(1).getEmail()).isEqualTo("woowacourse@woowa.com");
            softAssertions.assertThat(members.get(1).getPassword()).isEqualTo("pobi");
        });
    }

    private void resetTable() {
        final String deleteSql = "DELETE FROM member";
        jdbcTemplate.update(deleteSql);

        final String initializeIdSql = "ALTER TABLE member ALTER COLUMN ID RESTART WITH 1";
        jdbcTemplate.update(initializeIdSql);

        memberDao.insert(Member.of(1L, "test@test.com", "test"));
        memberDao.insert(Member.of(2L, "woowacourse@woowa.com", "pobi"));
    }
}

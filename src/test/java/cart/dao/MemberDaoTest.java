package cart.dao;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.domain.Member;

@JdbcTest
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);

        String deleteAllSql = "DELETE FROM member";
        jdbcTemplate.update(deleteAllSql);
    }

    @AfterEach
    void tearDown() {
        String deleteAllSql = "DELETE FROM member";
        jdbcTemplate.update(deleteAllSql);
    }

    @DisplayName("등록된 모든 멤버를 찾는다.")
    @Test
    void findAll() {
        // given
        String sql = "INSERT INTO member(email, password) VALUES ('jeomxon@gmail.com', 'password1')";
        jdbcTemplate.update(sql);

        // when
        List<Member> members = memberDao.findAll();

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(members).hasSize(1);
            softly.assertThat(members.get(0).getEmail()).isEqualTo("jeomxon@gmail.com");
            softly.assertThat(members.get(0).getPassword()).isEqualTo("password1");
        });
    }
}

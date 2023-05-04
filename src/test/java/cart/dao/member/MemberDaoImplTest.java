package cart.dao.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.entity.member.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setting() {
        memberDao = new MemberDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("사용자 데이터 전부를 가져온다.")
    void find_all_member() {
        // given
        jdbcTemplate.execute("INSERT INTO member(email, password) values ('ako@naver.com', 'ako1')");
        jdbcTemplate.execute("INSERT INTO member(email, password) values ('oz@naver.com', 'oz')");

        // when
        List<Member> result = memberDao.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("email로 member를 조회한다.")
    void find_memeber_by_email() {
        // given
        jdbcTemplate.execute("INSERT INTO member(email, password) values ('ako@naver.com', 'ako')");
        jdbcTemplate.execute("INSERT INTO member(email, password) values ('oz@naver.com', 'oz')");
        String email = "ako@naver.com";

        // when
        Optional<Member> result = memberDao.findByEmail(email);

        // then
        assertThat(result.get().getEmail()).isEqualTo(email);
        assertThat(result.get().getPassword()).isEqualTo("ako");
    }
}
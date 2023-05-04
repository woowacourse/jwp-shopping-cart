package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Member;
import cart.exception.custom.ResourceNotFoundException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberDaoTest {

    private static final String EMAIL = "email@naver.com";
    private static final String PASSWORD = "password";
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(dataSource);
    }

    @Test
    @DisplayName("저장된 모든 유저를 가져온다.")
    void find_all() {
        //given
        saveMember(new Member(EMAIL, PASSWORD));
        saveMember(new Member("nado@kakao.com", "password"));
        //when
        List<Member> actual = memberDao.findAll();
        //then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("이메일을 통해 멤버를 찾는다.")
    void find_by_email_success() {
        //given
        Member given = new Member(EMAIL, PASSWORD);
        saveMember(given);
        //when
        Member actual = memberDao.findByEmail(EMAIL);
        //then
        assertAll(
                () -> assertThat(actual).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(given),
                () -> assertThat(actual.getId()).isPositive());
    }

    @Test
    @DisplayName("존재하지 않는 이메일의 유저를 요청한다.")
    void find_by_email_fail_by_wrong_email() {
        //when && then
        assertThatThrownBy(() -> memberDao.findByEmail(EMAIL))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("해당하는 email의 member가 존재하지 않습니다.");
    }

    private void saveMember(Member member) {
        String sql = "insert into member(email, password) values (?,?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }
}

package cart.dao;

import cart.entity.Member;
import cart.exception.ServiceIllegalArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcMemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao.save(new Member("gksqlsl11@khu.ac.kr", "qlalfqjsgh"));
        memberDao.save(new Member("kong@google.com", "pw"));
    }

    @DisplayName("사용자가 저장되어있다면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"gksqlsl11@khu.ac.kr:qlalfqjsgh", "kong@google.com:pw"}, delimiter = ':')
    void isMemberExists_true(String email, String password) {
        Member member = new Member(email, password);
        assertThat(memberDao.isValidMember(member)).isTrue();
    }

    @DisplayName("사용자가 저장되어있지 않다면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"gksqlsl11@khu.ac.kr:qlalfqjsgh123", ":qlalfqjsgh", "gksqlsl11:qlalf", ":"}, delimiter = ':')
    void isMemberExists_false(String email, String password) {
        Member member = new Member(email, password);
        assertThat(memberDao.isValidMember(member)).isFalse();
    }

    @DisplayName("이메일이 저장되어있다면 true를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"gksqlsl11@khu.ac.kr", "kong@google.com"})
    void isEmailExists_true(String email) {
        assertThat(memberDao.isEmailExists(email)).isTrue();
    }

    @DisplayName("이메일이 저장되어있지 않다면 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"gksqlsl11@google.com", "", "      "})
    void isEmailExists_false(String email) {
        assertThat(memberDao.isEmailExists(email)).isFalse();
    }

    @DisplayName("중복되지 않은 사용자 정보를 저장할 수 있다.")
    @Test
    void save_success() {
        Member member = new Member("power@google.com", "power");

        memberDao.save(member);
        
        assertThat(memberDao.isValidMember(member)).isTrue();
    }

    @DisplayName("중복된 사용자는 저장할 수 없다.")
    @Test
    void save_fail() {
        Member member = new Member("power@google.com", "power");

        memberDao.save(member);
        assertThatThrownBy(() -> memberDao.save(member))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("이메일이 중복되었습니다.");
    }
}

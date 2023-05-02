package cart.dao;

import cart.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcMemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    @DisplayName("사용자가 저장되어있다면 true를 반환한다.")
    @Test
    void isMemberExists_true() {
        assertThat(memberDao.isMemberExists(new Member("gksqlsl11@khu.ac.kr", "qlalfqjsgh"))).isTrue();
    }

    @DisplayName("사용자가 저장되어있지 않다면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"gksqlsl11@khu.ac.kr:qlalfqjsgh123", ":qlalfqjsgh", "gksqlsl11:qlalf", ":"}, delimiter = ':')
    void isMemberExists_false(String email, String password) {
        Member member = new Member(email, password);
        assertThat(memberDao.isMemberExists(member)).isFalse();
    }
}

package cart.member.dao;

import cart.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberDaoTest {

    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setMemberDaoTest() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void 전체_멤버_조회() {
        List<Member> members = memberDao.selectAllMembers();
        assertThat(members.size()).isSameAs(2);
    }

    @ParameterizedTest
    @CsvSource(value = {"rg970604@naver.com:password", "yimsh66@naver.com:password"}, delimiter = ':')
    void 이메일과_비밀번호로_개별_멤버_조회(String email, String password) {
        Member member = memberDao.selectMemberByEmailAndPassword(email, password);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(password);
    }
}
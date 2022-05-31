package woowacourse.member.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.domain.Member;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberDaoTest {

    private final MemberDao memberDao;

    public MemberDaoTest(DataSource dataSource) {
        this.memberDao = new MemberDao(dataSource);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        String email = "wooteco@naver.com";
        Member member = Member.withEncrypt(email, "wooteco", "Wooteco1!");
        memberDao.save(member);
        assertThat(memberDao.existMemberByEmail(email)).isTrue();
    }

    @DisplayName("이메일로 회원을 찾아 반환한다.")
    @Test
    void findMemberByEmail() {
        Optional<Member> result = memberDao.findMemberByEmail("ari@wooteco.com");
        assertThat(result.get().getName()).isEqualTo("아리");
    }

    @DisplayName("존재하지 않는 이메일인 경우 빈 Optional을 반환한다.")
    @Test
    void findMemberByNotExistEmail() {
        Optional<Member> result = memberDao.findMemberByEmail("pobi@wooteco.com");
        assertThat(result).isEmpty();
    }

    @DisplayName("id로 회원을 찾아 반환한다.")
    @Test
    void findMemberById() {
        Optional<Member> result = memberDao.findMemberById(1L);
        assertThat(result.get().getName()).isEqualTo("아리");
    }

    @DisplayName("존재하지 않는 id인 경우 빈 Optional을 반환한다.")
    @Test
    void findMemberByNotExistId() {
        Optional<Member> result = memberDao.findMemberById(100L);
        assertThat(result).isEmpty();
    }
}

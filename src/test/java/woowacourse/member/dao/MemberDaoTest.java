package woowacourse.member.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.password.Password;
import woowacourse.member.domain.password.PlainPassword;

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
        PlainPassword plainPassword = new PlainPassword("Wooteco1!");
        Password password = plainPassword.encrypt();
        Member member = new Member(email, "wooteco", password);
        memberDao.save(member);
        assertThat(memberDao.existMemberByEmail(email)).isTrue();
    }

    @DisplayName("이메일로 회원을 찾아 반환한다.")
    @Test
    void findMemberByEmail() {
        Optional<Member> result = memberDao.findMemberByEmail("ari@wooteco.com");
        assertThat(result.get().getName()).isEqualTo("아리");
    }

    @DisplayName("이메일이 사용중일시 true를 반환한다.")
    @Test
    void existMemberByEmail() {
        String email = "rex@wooteco.com";
        boolean result = memberDao.existMemberByEmail(email);
        assertThat(result).isTrue();
    }

    @DisplayName("이메일이 사용중이지 않으면 false를 반환한다.")
    @Test
    void notExistMemberByEmail() {
        String email = "rex1@wooteco.com";
        boolean result = memberDao.existMemberByEmail(email);
        assertThat(result).isFalse();
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

    @DisplayName("id를 통해 회원을 찾아 회원 이름을 변경한다.")
    @Test
    void updateName() {
        memberDao.updateName(1L, "메아리");

        Optional<Member> result = memberDao.findMemberById(1L);
        assertThat(result.get().getName()).isEqualTo("메아리");
    }

    @DisplayName("id를 통해 회원을 찾아 회원 비밀번호를 변경한다.")
    @Test
    void updatePassword() {
        memberDao.updatePassword(1L, "NewPassword!");

        Optional<Member> result = memberDao.findMemberById(1L);
        assertThat(result.get().getPassword()).isEqualTo("NewPassword!");
    }

    @DisplayName("id를 통해 멤버 정보를 삭제한다.")
    @Test
    void deleteById() {
        memberDao.deleteById(1L);
        Optional<Member> member = memberDao.findMemberById(1L);
        assertThat(member).isEmpty();
    }
}

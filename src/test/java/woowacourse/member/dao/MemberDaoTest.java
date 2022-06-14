package woowacourse.member.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.domain.InputPassword;
import woowacourse.member.domain.Member;
import woowacourse.member.exception.MemberNotFoundException;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberDaoTest {

    private final MemberDao memberDao;

    public MemberDaoTest(DataSource dataSource) {
        this.memberDao = new MemberDao(dataSource);
    }

    @DisplayName("이메일로 회원을 찾아 반환한다.")
    @Test
    void findMemberByEmail() {
        Member member = memberDao.getByEmail("ari@wooteco.com");
        assertThat(member.getName()).isEqualTo("아리");
    }

    @DisplayName("존재하지 않는 이메일인 경우 예외가 발생한다.")
    @Test
    void findMemberByNotExistEmail() {
        assertThatThrownBy(
                () -> memberDao.getByEmail("pobi@wooteco.com")
        ).isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("해당 이메일로 가입된 회원은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("해당 이메일이 존재한다면 true를 반환한다.")
    void existMemberByEmailWhenTrue() {
        assertThat(memberDao.existsByEmail("ari@wooteco.com")).isTrue();
    }

    @Test
    @DisplayName("해당 이메일이 존재하지 않는다면 false를 반환한다.")
    void existMemberByEmailWhenFalse() {
        assertThat(memberDao.existsByEmail("woni@wooteco.com")).isFalse();
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        String email = "wooteco@naver.com";
        Member member = new Member(email, "wooteco", new InputPassword("Wooteco1!"));
        memberDao.save(member);
        assertThat(memberDao.existsByEmail(email)).isTrue();
    }

    @DisplayName("id로 회원을 찾아 반환한다.")
    @Test
    void findMemberById() {
        Member member = memberDao.getById(1L);
        assertThat(member.getName()).isEqualTo("아리");
    }

    @DisplayName("존재하지 않는 id인 경우 예외가 발생한다.")
    @Test
    void findMemberByNotExistId() {
        assertThatThrownBy(
                () -> memberDao.getById(100L)
        ).isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }

    @DisplayName("id를 통해 회원을 찾아 회원 이름을 변경한다.")
    @Test
    void updateName() {
        memberDao.updateNameById(1L, "메아리");

        Member member = memberDao.getById(1L);
        assertThat(member.getName()).isEqualTo("메아리");
    }

    @DisplayName("id를 통해 회원을 찾아 회원 비밀번호를 변경한다.")
    @Test
    void updatePassword() {
        memberDao.updatePasswordById(1L, "InputPassword!");

        Member member = memberDao.getById(1L);
        assertThat(member.getPassword()).isEqualTo("InputPassword!");
    }

    @DisplayName("id를 통해 멤버 정보를 삭제한다.")
    @Test
    void deleteById() {
        memberDao.deleteById(1L);

        assertThatThrownBy(
                () -> memberDao.getById(1L)
        ).isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");

    }
}

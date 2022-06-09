package woowacourse.member.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.domain.Member;

@JdbcTest
@Sql("file:src/test/resources/test_member.sql")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void beforeEach() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("회원 정보를 저장한다.")
    @Test
    void save() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        assertThatCode(() -> memberDao.save(member))
                .doesNotThrowAnyException();
    }

    @DisplayName("이메일이 존재하는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, true", "abc@naver.com, false"})
    void existsEmail(String email, boolean expected) {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        boolean actual = memberDao.existsEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("id가 존재하는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1, true", "2, false"})
    void checkIdExistence(long id, boolean expected) {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        boolean actual = memberDao.checkIdExistence(id);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("회원 id로 회원정보를 조회한다.")
    @Test
    void findByEmail() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        Member foundMember = memberDao.findById(1L);

        assertThat(foundMember).isEqualTo(member);
    }

    @DisplayName("이메일과 비밀번호에 해당하는 회원정보가 없을 경우 empty인 Optional을 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, 1q2w3e4r@", "abc@naver.com, 1q2w3e4r!", "abc@naver.com, 1q2w3e4r@"})
    void findByEmailAndPassword_Null(String email, String password) {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        Optional<Member> foundMember = memberDao.findByEmailAndPassword(email, password);
        boolean actual = foundMember.isEmpty();

        assertThat(actual).isTrue();
    }

    @DisplayName("회원 id와 비밀번호를 받아, 해당 비밀번호가 일치하는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1q2w3e4r!, true", "1q2w3e4r@, false"})
    void checkPassword(String password, boolean expected) {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        boolean actual = memberDao.checkPassword(1L, password);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("회원 id에 해당하는 회원의 닉네임을 변경한다.")
    @Test
    void updateNicknameById() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberDao.updateNicknameById(1L, "바꾼닉네임");
        Member foundMember = memberDao.findById(1L);

        assertThat(foundMember.getNickname()).isEqualTo("바꾼닉네임");
    }

    @DisplayName("회원 id에 해당하는 회원의 비밀번호를 변경한다.")
    @Test
    void updatePasswordById() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberDao.updatePasswordById(1L, "1q2w3e4r@");
        Member foundMember = memberDao.findById(1L);

        assertThat(foundMember.getPassword()).isEqualTo("1q2w3e4r@");
    }

    @DisplayName("회원 id에 해당하는 회원을 삭제한다.")
    @Test
    void deleteById() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberDao.deleteById(1L);
        boolean actual = memberDao.existsEmail(member.getEmail());

        assertThat(actual).isFalse();
    }
}

package woowacourse.member.dao;

import static org.assertj.core.api.Assertions.assertThat;

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

    private static final Member MEMBER = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void beforeEach() {
        memberDao = new MemberDao(jdbcTemplate);
        memberDao.save(MEMBER);
    }

    @DisplayName("이메일이 존재하는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, true", "abc@naver.com, false"})
    void existsEmail(String email, boolean expected) {
        boolean actual = memberDao.existsEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("id가 존재하는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1, true", "2, false"})
    void checkIdExistence(long id, boolean expected) {
        boolean actual = memberDao.checkIdExistence(id);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("회원 id로 회원정보를 조회한다.")
    @Test
    void findByEmail() {
        Member foundMember = memberDao.findById(1L);

        assertThat(foundMember).isEqualTo(MEMBER);
    }

    @DisplayName("이메일과 비밀번호에 해당하는 회원정보가 없을 경우 empty인 Optional을 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, 1q2w3e4r@", "abc@naver.com, 1q2w3e4r!", "abc@naver.com, 1q2w3e4r@"})
    void findByEmailAndPassword_Null(String email, String password) {
        Optional<Member> foundMember = memberDao.findByEmailAndPassword(email, password);
        boolean actual = foundMember.isEmpty();

        assertThat(actual).isTrue();
    }

    @DisplayName("회원 id와 비밀번호를 받아, 해당 비밀번호가 일치하는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1q2w3e4r!, true", "1q2w3e4r@, false"})
    void checkPassword(String password, boolean expected) {
        boolean actual = memberDao.checkPassword(1L, password);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("회원 id에 해당하는 회원의 닉네임을 변경한다.")
    @Test
    void updateNicknameById() {
        memberDao.updateNicknameById(1L, "바꾼닉네임");
        Member foundMember = memberDao.findById(1L);

        assertThat(foundMember.getNickname()).isEqualTo("바꾼닉네임");
    }

    @DisplayName("회원 id에 해당하는 회원의 비밀번호를 변경한다.")
    @Test
    void updatePasswordById() {
        memberDao.updatePasswordById(1L, "1q2w3e4r@");
        Member foundMember = memberDao.findById(1L);

        assertThat(foundMember.getPassword()).isEqualTo("1q2w3e4r@");
    }

    @DisplayName("회원 id에 해당하는 회원을 삭제한다.")
    @Test
    void deleteById() {
        memberDao.deleteById(1L);
        boolean actual = memberDao.existsEmail(MEMBER.getEmail());

        assertThat(actual).isFalse();
    }
}

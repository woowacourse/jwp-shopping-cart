package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
import woowacourse.shoppingcart.domain.Member;

@JdbcTest
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

        memberDao.save(member);
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

    @DisplayName("이메일로 회원정보를 조회한다.")
    @Test
    void findByEmail() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        Member foundMember = memberDao.findByEmail("abc@woowahan.com")
                .orElseGet(() -> fail("실패"));

        assertThat(foundMember).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(member);
    }

    @DisplayName("회원의 닉네임을 변경한다.")
    @Test
    void updateNicknameByEmail() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberDao.updateNicknameByEmail(member.getEmail(), "바꾼닉네임");

        Member foundMember = memberDao.findByEmail("abc@woowahan.com")
                .orElseGet(() -> fail("실패"));
        assertThat(foundMember.getNickname()).isEqualTo("바꾼닉네임");
    }

    @DisplayName("회원의 비밀번호를 변경한다.")
    @Test
    void updatePasswordByEmail() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberDao.updatePasswordByEmail(member.getEmail(), "1q2w3e4r@");

        Member foundMember = memberDao.findByEmail("abc@woowahan.com")
                .orElseGet(() -> fail("실패"));
        assertThat(foundMember.getPassword()).isEqualTo("1q2w3e4r@");
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void deleteByEmail() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberDao.deleteByEmail(member.getEmail());
        boolean actual = memberDao.existsEmail(member.getEmail());

        assertThat(actual).isFalse();
    }
}

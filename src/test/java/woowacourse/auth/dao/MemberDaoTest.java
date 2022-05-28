package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;

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
import woowacourse.auth.domain.Member;

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

}
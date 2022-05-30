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
        Member member = new Member(email, "wooteco", "Wooteco1!");
        memberDao.save(member);
        assertThat(memberDao.existMemberByEmail(email)).isTrue();
    }

    @DisplayName("이메일로 비밀번호를 찾아 반환한다.")
    @Test
    void findPasswordByEmail() {
        Optional<String> result = memberDao.findPasswordByEmail("ari@wooteco.com");
        assertThat(result.get()).isEqualTo("1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f");
    }

    @DisplayName("이메일로 비밀번호를 찾아 반환한다.")
    @Test
    void findPasswordByNotExistEmail() {
        Optional<String> result = memberDao.findPasswordByEmail("pobi@wooteco.com");
        assertThat(result).isEmpty();
    }
}

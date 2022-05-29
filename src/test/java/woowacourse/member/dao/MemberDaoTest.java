package woowacourse.member.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import woowacourse.helper.fixture.MemberFixture;
import woowacourse.member.domain.Member;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberDaoTest {

    @Autowired
    private DataSource dataSource;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(dataSource);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        Member member = MemberFixture.createMember(EMAIL, PASSWORD, NAME);

        Long id = memberDao.save(member);
        assertThat(id).isNotNull();
    }

    @DisplayName("이메일을 중복 확인한다.")
    @Test
    void isEmailExist() {
        Member member = MemberFixture.createMember(EMAIL, PASSWORD, NAME);
        memberDao.save(member);

        assertThat(memberDao.isEmailExist(EMAIL)).isTrue();
    }
}

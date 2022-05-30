package woowacourse.member.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.domain.Member;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

}

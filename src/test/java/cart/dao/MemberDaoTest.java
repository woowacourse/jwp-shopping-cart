package cart.dao;

import cart.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/data-test.sql"})
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("전체 사용자를 조회한다.")
    @Test
    void findAll() {
        // when
        List<Member> members = memberDao.findAll();

        // then
        Assertions.assertThat(members.get(0))
                .hasFieldOrPropertyWithValue("email", "a@a.com")
                .hasFieldOrPropertyWithValue("password", "password1");
    }

    @DisplayName("email로 사용자를 조회한다.")
    @Test
    void findByEmail() {
        // when
        Long memberId = memberDao.findIdByEmail("a@a.com");

        // then
        Assertions.assertThat(memberId).isEqualTo(1L);
    }
}
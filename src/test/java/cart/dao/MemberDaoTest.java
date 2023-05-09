package cart.dao;

import cart.entity.MemberEntity;
import cart.exception.NotFoundMemberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/data-test.sql")
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("모든 회원을 조회한다.")
    @Test
    void findAll() {
        // when
        List<MemberEntity> responses = memberDao.findAll();

        // then
        Assertions.assertAll(
                () -> assertThat(responses.size()).isEqualTo(2),
                () -> assertThat(responses.get(0).getEmail()).isEqualTo("a@a.com"),
                () -> assertThat(responses.get(1).getEmail()).isEqualTo("b@b.com")
        );
    }

    @DisplayName("이메일로 회원을 조회한다.")
    @Test
    void findByEmail() {
        // when
        MemberEntity responses = memberDao.findByEmail("a@a.com")
                .orElseThrow(() -> NotFoundMemberException.EXCEPTION);

        // then
        assertThat(responses.getEmail()).isEqualTo("a@a.com");
    }
}

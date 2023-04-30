package cart.dao;

import cart.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("모든 멤버를 조회한다")
    void findAll() {
        final List<MemberEntity> result = memberDao.findAll();

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(result.get(0).getEmail()).isEqualTo("a@a.com"),
                () -> assertThat(result.get(0).getPassword()).isEqualTo("password1"),
                () -> assertThat(result.get(1).getEmail()).isEqualTo("b@b.com"),
                () -> assertThat(result.get(1).getPassword()).isEqualTo("password2")
        );
    }
}

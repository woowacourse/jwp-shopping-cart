package cart.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import cart.entity.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/test.sql")
class MemberDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("전체 사용자를 반환한다.")
    void findAll_success() {
        // when
        List<MemberEntity> all = memberDao.findAll();
        MemberEntity member = all.get(0);

        // then
        assertAll(
                () -> assertThat(all).hasSize(1),
                () -> assertThat(member.getEmail()).isEqualTo("test@email.com"),
                () -> assertThat(member.getPassword()).isEqualTo("12345678")
        );
    }

    @Test
    @DisplayName("email 정보로 사용자를 조회한다.")
    void findByEmail_success() {
        // given
        String email = "test@email.com";

        // when
        MemberEntity member = memberDao.findByEmail(email);

        // then
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(1),
                () -> assertThat(member.getEmail()).isEqualTo("test@email.com"),
                () -> assertThat(member.getPassword()).isEqualTo("12345678")
        );
    }
}

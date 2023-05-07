package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.entity.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("전체 회원 조회 테스트")
    @Test
    void Should_Success_When_FindAll() {
        List<MemberEntity> members = memberDao.findAll();

        assertThat(members).hasSize(2);
    }

    @DisplayName("이메일과 비밀번호와 일치하는 회원 조회 테스트")
    @Test
    void Should_Success_When_FindByEmailWithPassword() {
        MemberEntity member = memberDao.findByEmailWithPassword("a@a.com", "password1").get(0);

        assertAll(
                () -> assertThat(member.getEmail()).isEqualTo("a@a.com"),
                () -> assertThat(member.getPassword()).isEqualTo("password1")
        );
    }
}

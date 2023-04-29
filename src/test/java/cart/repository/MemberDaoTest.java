package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("회원이 정상적으로 저장되어야 한다.")
    void save_success() {
        // given
        Member member = new Member("glen@naver.com", "123456");

        // when
        memberDao.save(member);

        // then
        assertThat(memberDao.existsByEmail("glen@naver.com"))
                .isTrue();
    }
}

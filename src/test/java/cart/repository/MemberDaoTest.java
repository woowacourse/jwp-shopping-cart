package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Member;
import cart.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
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

    @Test
    @DisplayName("모든 회원이 정상적으로 조회되어야 한다.")
    void findAll_success() {
        // given
        for (int i = 0; i < 5; i++) {
            Member member = new Member("glen" + i, "123456");
            memberDao.save(member);
        }

        // when
        List<MemberEntity> allMembers = memberDao.findAll();

        // then
        assertThat(allMembers)
                .hasSize(5);
    }

    @Test
    @DisplayName("회원의 이메일과 비밀번호로 조회할 수 있어야 한다.")
    void findByEmailAndPassword_success() {
        // given
        Member member = new Member("glen@naver.com", "123456");
        memberDao.save(member);

        // when
        Optional<Long> findMember = memberDao.findByEmailAndPassword("glen@naver.com", "123456");

        // then
        assertThat(findMember)
                .isPresent();
    }

    @Test
    @DisplayName("회원의 이메일과 비밀번호로 조회할 때 비밀번호가 다르면 조회가 되면 안 된다.")
    void findByEmailAndPassword_invalidPassword() {
        // given
        Member member = new Member("glen@naver.com", "123456");
        memberDao.save(member);

        // when
        Optional<Long> findMember = memberDao.findByEmailAndPassword("glen@naver.com", "111111");

        // then
        assertThat(findMember)
                .isEmpty();
    }
}

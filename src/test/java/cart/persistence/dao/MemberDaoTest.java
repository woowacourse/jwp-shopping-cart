package cart.persistence.dao;

import cart.domain.member.Member;
import cart.dto.LoginDto;
import cart.persistence.dao.MemberDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = MemberDao.class)
class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;
    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        member1 = new Member(1L, "test1", "pass1");
        member2 = new Member(2L, "test2", "pass2");
    }

    @Test
    @DisplayName("모든 member를 꺼내온다")
    void findAllTest() {
        List<Member> all = memberDao.findAll();
        assertAll(
                () -> assertThat(all.size()).isEqualTo(2),
                () -> assertThat(all.get(0)).isEqualTo(member1),
                () -> assertThat(all.get(1)).isEqualTo(member2)
        );
    }

    @Test
    @DisplayName("해당 member를 포함하고 있다")
    void containsTest() {
        assertAll(
                () -> assertThat(memberDao.contains(member1)).isTrue(),
                () -> assertThat(memberDao.contains(member1)).isTrue()
        );
    }

    @Test
    @DisplayName("email과 password가 일치하는 member를 찾는다")
    void findByEmailAndPasswordTest() {
        LoginDto loginDto1 = new LoginDto(member1.getEmail(), member1.getPassword());
        LoginDto loginDto2 = new LoginDto(member2.getEmail(), member2.getPassword());

        Member found1 = memberDao.findByEmailAndPassword(loginDto1).get();
        Member found2 = memberDao.findByEmailAndPassword(loginDto2).get();

        assertAll(
                () -> assertThat(found1).isEqualTo(member1),
                () -> assertThat(found2).isEqualTo(member2)
        );

    }
}

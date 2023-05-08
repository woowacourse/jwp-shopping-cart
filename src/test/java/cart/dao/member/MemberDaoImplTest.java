package cart.dao.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dto.member.MemberRequest;
import cart.entity.member.Email;
import cart.entity.member.Member;
import cart.entity.member.Password;
import cart.entity.member.Role;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setting() {
        memberDao = new MemberDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("사용자 데이터 전부를 가져온다.")
    void find_all_member() {
        // given
        List<Member> members = List.of(new Member(1L, new Email("ako@wooteco.com"), new Password("ako"), Role.USER),
            new Member(2L, new Email("oz@wooteco.com"), new Password("oz"), Role.USER),
            new Member(3L, new Email("admin@wooteco.com"), new Password("admin"), Role.ADMIN));

        // when
        List<Member> result = memberDao.findAll();

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.equals(members));
    }

    @Test
    @DisplayName("email로 member를 조회한다.")
    void find_memeber_by_email() {
        // given
        String email = "ako@wooteco.com";
        String password = "ako";
        // when
        Optional<Member> result = memberDao.findByEmailAndPassword(email, password);

        // then
        assertThat(result.get().getEmail()).isEqualTo(email);
        assertThat(result.get().getPassword()).isEqualTo(password);
    }
}
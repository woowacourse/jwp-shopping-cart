package cart.domain.product.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("이메일로 회원을 찾아온다.")
    public void testFindByEmail() {
        //given
        final MemberDao memberDao = new MemberDao(jdbcTemplate);
        final String email = "email@email.com";
        final Member member = new Member(1L, email, "password", LocalDateTime.now(),
            LocalDateTime.now());
        memberDao.save(member);

        //when
        final Optional<Member> memberOptional = memberDao.findByEmail(email);

        //then
        assertThat(memberOptional.isPresent()).isTrue();
        final Member findMember = memberOptional.get();
        assertThat(findMember).isEqualTo(member);
    }
}

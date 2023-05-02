package cart.domain.member.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
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

    @Test
    @DisplayName("존재하지 않는 회원을 찾는다.")
    public void testFindByEmailNull() {
        //given
        final MemberDao memberDao = new MemberDao(jdbcTemplate);
        final String email = "email@email.com";

        //when
        final Optional<Member> memberOptional = memberDao.findByEmail(email);

        //then
        assertThat(memberOptional.isPresent()).isFalse();
    }

    @Test
    @DisplayName("모든 회원을 찾아온다.")
    public void testFindAll() {
        //given
        final MemberDao memberDao = new MemberDao(jdbcTemplate);
        final Member member1 = new Member(1L, "email1@email.com", "password1", LocalDateTime.now(),
            LocalDateTime.now());
        final Member member2 = new Member(2L, "email2@email.com", "password2", LocalDateTime.now(),
            LocalDateTime.now());
        memberDao.save(member1);
        memberDao.save(member2);

        //when
        final List<Member> members = memberDao.findAll();

        //then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.get(0).getId()).isEqualTo(member1.getId());
        assertThat(members.get(1).getId()).isEqualTo(member2.getId());
    }
}

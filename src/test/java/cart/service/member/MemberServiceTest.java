package cart.service.member;

import cart.domain.member.Member;
import cart.dto.member.MemberLoginRequestDto;
import cart.dto.member.MembersResponseDto;
import cart.exception.MemberNotFoundException;
import cart.exception.PasswordInvalidException;
import cart.repository.member.MemberRepository;
import cart.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("INSERT INTO member(id, email, password) VALUES (1, 'test1@test.com', '!!abc123'), (2, 'test2@test.com', '!!abc123')");
    }

    @Test
    @DisplayName("모든 사용자를 찾는다.")
    void find_all_members() {
        // when
        MembersResponseDto result = memberService.findAll();

        // then
        assertAll(
                () -> assertThat(result.getMembers().size()).isEqualTo(2),
                () -> assertThat(result.getMembers().get(0).getEmail()).isEqualTo("test1@test.com")
        );
    }

    @Test
    @DisplayName("사용자 한 명을 찾는다.")
    void returns_member() {
        // given
        MemberLoginRequestDto req = MemberLoginRequestDto.from("test1@test.com", "!!abc123");

        // when
        Member member = memberService.findMember(req);

        // then
        assertThat(member.getEmail()).isEqualTo(req.getEmail());
    }

    @Test
    @DisplayName("로그인 이메일이 다르면 예외를 발생시킨다.")
    void throws_exception_when_email_invalid() {
        // given
        MemberLoginRequestDto req = MemberLoginRequestDto.from("toast@test.com", "!!abc123");

        // when & then
        assertThatThrownBy(() -> memberService.findMember(req))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("로그인 패스워드가 다르면 예외를 발생시킨다.")
    void throws_exception_when_password_invalid() {
        // given
        MemberLoginRequestDto req = MemberLoginRequestDto.from("test1@test.com", "!!hello99");

        // when & then
        assertThatThrownBy(() -> memberService.findMember(req))
                .isInstanceOf(PasswordInvalidException.class);
    }
}

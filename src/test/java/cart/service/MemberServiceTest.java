package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.domain.Member;
import cart.exception.MemberNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    MemberService memberService;
    Member member;

    @BeforeEach
    void setUp() {
        MemberDao memberDao = new MemberDao(jdbcTemplate);
        memberService = new MemberService(memberDao);
        member = new Member("gray@wooteco.com", "wooteco", "그레이");
        memberDao.insert(member);
    }

    @Test
    @DisplayName("모든 회원을 조회한다.")
    void findOneSuccess() {
        List<MemberResponse> allMembers = memberService.findAll();

        assertThat(allMembers).hasSizeGreaterThan(0);
    }

    @Test
    @DisplayName("이메일, 비밀번호에 해당하는 회원을 조회한다.")
    void findByEmailSuccess() {
        MemberResponse memberResponse = memberService.findByEmailAndPassword(member.getEmail(),
                member.getPassword());

        assertAll(
                () -> assertThat(memberResponse.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(memberResponse.getPassword()).isEqualTo(member.getPassword()),
                () -> assertThat(memberResponse.getName()).isEqualTo(member.getName())
        );
    }

    @Test
    @DisplayName("이메일에 해당하는 회원이 없으면 예외가 발생한다.")
    void findByEmailFailWithWrongEmail() {
        assertThatThrownBy(() -> memberService.findByEmailAndPassword("wrong@wrong.com",
                member.getPassword()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("일치하는 회원이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("이메일에 해당하는 회원의 비밀번호가 일치하지 않으면 예외가 발생한다.")
    void findByEmailFailWithWrongPassword() {
        assertThatThrownBy(() -> memberService.findByEmailAndPassword(member.getEmail(),
                "wrongPW"))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }
}

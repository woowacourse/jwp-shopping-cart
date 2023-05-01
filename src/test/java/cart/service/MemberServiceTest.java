package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import cart.domain.member.Member;
import cart.dto.MemberRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    private final MemberRegisterRequest memberRegisterRequest =
            new MemberRegisterRequest("SeongHa", "seongha@gmail.com", "1234");


    @Test
    @DisplayName("사용자를 등록한다.")
    void register() {
        // when, then
        Assertions.assertDoesNotThrow(() -> memberService.register(memberRegisterRequest));
    }

    @Test
    @DisplayName("모든 사용자를 조회한다.")
    void findAll() {
        // given
        memberService.register(memberRegisterRequest);

        // when
        List<Member> members = memberService.findAll();
        Member member1 = members.get(0);

        // then
        assertAll(
                () -> assertThat(members).hasSize(1),
                () -> assertThat(member1.getNickname()).isEqualTo(memberRegisterRequest.getNickname()),
                () -> assertThat(member1.getEmail()).isEqualTo(memberRegisterRequest.getEmail()),
                () -> assertThat(member1.getPassword()).isEqualTo(memberRegisterRequest.getPassword())
        );
    }
}

package cart.service;

import static cart.fixture.MemberFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import cart.dto.MemberFindResponse;
import cart.dto.MemberRegisterRequest;
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

    @Test
    @DisplayName("사용자를 등록한다.")
    void register() {
        // when, then
        assertDoesNotThrow(() -> memberService.register(MEMBER_REGISTER_REQUEST));
    }

    @Test
    @DisplayName("등록할 사용자 닉네임이 이미 존재하면 예외가 발생한다.")
    void register_throw_nickname_duplicate() {
        // given
        memberService.register(MEMBER_REGISTER_REQUEST);
        MemberRegisterRequest duplicateNicknameMember =
                new MemberRegisterRequest(DUMMY_NICKNAME, "new" + DUMMY_EMAIL, "1234");

        // when, then
        assertThatThrownBy(() -> memberService.register(duplicateNicknameMember))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 닉네임입니다. 다시 입력해주세요.");
    }

    @Test
    @DisplayName("등록할 사용자 이메일이 이미 존재하면 예외가 발생한다.")
    void register_throw_email_duplicate() {
        // given
        memberService.register(MEMBER_REGISTER_REQUEST);
        MemberRegisterRequest duplicateNicknameMember =
                new MemberRegisterRequest("new" + DUMMY_NICKNAME, DUMMY_EMAIL, "1234");

        // when, then
        assertThatThrownBy(() -> memberService.register(duplicateNicknameMember))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일입니다. 다시 입력해주세요.");
    }


    @Test
    @DisplayName("모든 사용자를 조회한다.")
    void findAll() {
        // given
        memberService.register(MEMBER_REGISTER_REQUEST);

        // when
        List<MemberFindResponse> memberFindResponses = memberService.findAll();
        MemberFindResponse memberFindResponse1 = memberFindResponses.get(0);

        // then
        assertAll(
                () -> assertThat(memberFindResponses).hasSize(1),
                () -> assertThat(memberFindResponse1.getNickname()).isEqualTo(MEMBER_REGISTER_REQUEST.getNickname()),
                () -> assertThat(memberFindResponse1.getEmail()).isEqualTo(MEMBER_REGISTER_REQUEST.getEmail()),
                () -> assertThat(memberFindResponse1.getPassword()).isEqualTo(MEMBER_REGISTER_REQUEST.getPassword())
        );
    }
}

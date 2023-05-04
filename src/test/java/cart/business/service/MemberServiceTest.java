package cart.business.service;

import cart.business.domain.member.Member;
import cart.business.domain.member.MemberEmail;
import cart.business.domain.member.MemberId;
import cart.business.domain.member.MemberPassword;
import cart.business.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("해당 정보(ID, PW)를 가진 멤버가 존재하지 않는 경우, 예외를 던진다")
    void test_find_and_return_id() {
        // given
        Member dummyMember = new Member(new MemberId(1), new MemberEmail("a@a.com"), new MemberPassword("1234"));

        // when
        when(memberRepository.findAndReturnId(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> memberService.findAndReturnId(dummyMember))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 식별자를 가진 멤버가 존재하지 않을 경우, 예외를 던진다")
    void test_validate_user() {
        // given, when
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> memberService.validateExists(any()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

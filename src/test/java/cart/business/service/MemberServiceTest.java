package cart.business.service;

import cart.business.domain.member.Member;
import cart.business.domain.member.MemberEmail;
import cart.business.domain.member.MemberId;
import cart.business.domain.member.MemberPassword;
import cart.business.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    private static Member memberFixture;

    @BeforeAll
    static void setup() {
        memberFixture = new Member(new MemberId(1), new MemberEmail("a@a.com"), new MemberPassword("1234"));
    }

    @Test
    @DisplayName("해당 정보(ID, PW)를 가진 멤버가 존재하지 않는 경우, 예외를 던진다")
    void test_find_and_return_id_exception() {
        // when
        when(memberRepository.findAndReturnId(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> memberService.findAndReturnId(memberFixture))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 정보(ID, PW)를 가진 멤버가 존재하는 경우, ID를 정상 반환한다")
    void test_find_and_return_id() {
        // when
        when(memberRepository.findAndReturnId(any())).thenReturn(Optional.of(memberFixture.getId()));

        // then
        assertThat(memberService.findAndReturnId(memberFixture)).isEqualTo(memberFixture.getId());
    }

    @Test
    @DisplayName("해당 식별자를 가진 멤버가 존재하지 않을 경우, 예외를 던진다")
    void test_validate_user_exception() {
        // when
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> memberService.validateExists(any()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 식별자를 가진 멤버가 존재하는 경우, 예외를 던지지 않는다")
    void test_validate_user() {
        // when
        when(memberRepository.findById(any())).thenReturn(Optional.of(memberFixture));

        // then
        assertThatCode(() -> memberService.validateExists(memberFixture.getId()))
                .doesNotThrowAnyException();
    }
}

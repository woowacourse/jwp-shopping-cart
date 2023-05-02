package cart.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.dto.MemberCreateRequest;
import cart.dto.MemberCreateResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원을 생성한다.")
    public void testCreate() {
        //given
        final MemberCreateRequest request = new MemberCreateRequest("test@test.com", "password");
        final Member member = new Member(1L, request.getEmail(), request.getPassword(),
            LocalDateTime.now(), LocalDateTime.now());
        given(memberDao.findByEmail(any()))
            .willReturn(Optional.empty());
        given(memberDao.save(any()))
            .willReturn(member);

        //when
        final MemberCreateResponse response = memberService.create(request);

        //then
        assertThat(response.getId()).isEqualTo(member.getId());
        assertThat(response.getEmail()).isEqualTo(member.getEmail());
        assertThat(response.getCreatedAt()).isEqualTo(member.getCreatedAt());
        assertThat(response.getUpdatedAt()).isEqualTo(member.getUpdatedAt());
    }

    @Test
    @DisplayName("회원을 생성 실패 - 중복 이메일")
    public void testCreateDuplicateEmail() {
        //given
        final MemberCreateRequest request = new MemberCreateRequest("test@test.com", "password");
        final Member member = new Member(1L, request.getEmail(), request.getPassword(),
            LocalDateTime.now(), LocalDateTime.now());
        given(memberDao.findByEmail(any()))
            .willReturn(Optional.of(member));

        //when
        //then
        Assertions.assertThatThrownBy(() -> memberService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

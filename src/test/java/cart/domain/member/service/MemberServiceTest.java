package cart.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.dto.MemberCreateRequest;
import cart.dto.MemberCreateResponse;
import cart.dto.MemberResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    @Mock
    private PasswordEncoder passwordEncoder;

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
        given(passwordEncoder.encode(anyString()))
            .willReturn("encoded password");

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
        assertThatThrownBy(() -> memberService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 회원을 조회한다.")
    public void testFindAll() {
        //given
        final List<Member> members = List.of(
            new Member(1L, "test1@test.com", "password1", LocalDateTime.now(), LocalDateTime.now()),
            new Member(2L, "test2@test.com", "password2", LocalDateTime.now(), LocalDateTime.now())
        );
        given(memberDao.findAll())
            .willReturn(members);

        //when
        final List<MemberResponse> result = memberService.findAll();

        //then
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getEmail()).isEqualTo(members.get(i).getEmail());
            assertThat(result.get(i).getPassword()).isEqualTo(members.get(i).getPassword());
        }
    }
}

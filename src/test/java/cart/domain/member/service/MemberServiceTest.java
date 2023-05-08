package cart.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cart.dao.MemberDao;
import cart.domain.member.dto.MemberCreateDto;
import cart.domain.member.dto.MemberInformation;
import cart.domain.member.dto.MemberResponse;
import cart.domain.member.entity.Member;
import cart.dto.MemberCreateRequest;
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

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원을 생성한다.")
    public void testCreate() {
        //given
        final MemberInformation memberInformation = new MemberInformation("test@test.com",
            "password");
        final Member member = new Member(1L, memberInformation.getEmail(),
            memberInformation.getPassword(),
            LocalDateTime.now(), LocalDateTime.now());
        given(memberDao.findByEmail(any()))
            .willReturn(Optional.empty());
        given(memberDao.save(any()))
            .willReturn(member);

        //when
        final MemberCreateDto memberCreateDto = memberService.create(memberInformation);

        //then
        assertThat(memberCreateDto)
            .extracting("id", "email", "createdAt", "updatedAt")
            .containsExactly(
                member.getId(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getUpdatedAt()
            );
    }

    @Test
    @DisplayName("회원을 생성 실패 - 중복 이메일")
    public void testCreateDuplicateEmail() {
        //given
        final MemberInformation memberInformation = new MemberInformation("test@test.com",
            "password");
        final Member member = new Member(1L, memberInformation.getEmail(), memberInformation.getPassword(),
            LocalDateTime.now(), LocalDateTime.now());
        given(memberDao.findByEmail(any()))
            .willReturn(Optional.of(member));

        //when
        //then
        assertThatThrownBy(() -> memberService.create(memberInformation))
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
            assertThat(result.get(i))
                .extracting("email", "password")
                .containsExactly(
                    members.get(i).getEmail(),
                    members.get(i).getPassword()
                );
        }
    }
}

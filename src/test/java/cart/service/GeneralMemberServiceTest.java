package cart.service;

import cart.domain.Member;
import cart.repository.MemberRepository;
import cart.service.request.MemberCreateRequest;
import cart.service.response.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class GeneralMemberServiceTest {
    @MockBean
    MemberRepository memberRepository;

    @Autowired
    GeneralMemberService generalMemberService;

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        // given
        given(memberRepository.save(any())).willReturn(1L);

        // when
        final MemberCreateRequest request = new MemberCreateRequest("헤나", "test@test.com", "test");
        final long saveMemberId = generalMemberService.save(request);

        // then
        assertThat(saveMemberId).isEqualTo(1L);
    }

    @DisplayName("전체 회원을 조회한다.")
    @Test
    void findAll() {
        // given
        final Member memberHyena = new Member(1L, "헤나", "test@test.com", "test");
        final Member memberTony = new Member(2L, "토니", "test2@test.com", "test2");
        given(memberRepository.findAll()).willReturn(List.of(memberHyena, memberTony));

        // when
        final List<MemberResponse> findMembers = generalMemberService.findAll();

        // then
        assertThat(findMembers)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new MemberResponse(1L, "헤나", "test@test.com", "test"),
                        new MemberResponse(2L, "토니", "test2@test.com", "test2")
                );
    }

    @DisplayName("회원을 회원 번호를 통해 삭제한다.")
    @Test
    void deleteByMemberId() {
        // given
        given(memberRepository.deleteByMemberId(anyLong())).willReturn(1L);

        // when
        final long deleteMemberId = generalMemberService.deleteByMemberId(1L);

        // then
        assertThat(deleteMemberId).isEqualTo(1L);
    }
}

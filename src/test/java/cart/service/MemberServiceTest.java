package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cart.dao.MemberDao;
import cart.dto.MemberAuthDto;
import cart.dto.response.MemberResponse;
import cart.entity.MemberEntity;
import cart.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDao memberDao;

    @Test
    @DisplayName("모든 멤버를 조회한다.")
    void findMembers() throws JsonProcessingException {
        final MemberEntity memberA = new MemberEntity(1L, "a@naver.com", "password1");
        final MemberEntity memberB = new MemberEntity(2L, "b@naver.com", "password2");

        given(memberDao.findAll()).willReturn(List.of(memberA, memberB));

        final List<MemberResponse> result = memberService.findMembers();

        final List<MemberResponse> expected = List.of(
                MemberResponse.from(memberA),
                MemberResponse.from(memberB)
        );
        assertThat(objectMapper.writeValueAsString(result)).isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @Nested
    @DisplayName("이메일과 비밀번호로 멤버 조회 시")
    class FindMember {

        @Test
        @DisplayName("멤버가 존재하면 멤버를 반환한다.")
        void findMember() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto("a@a.com", "password");
            given(memberDao.findByEmailAndPassword(any(), any())).willReturn(
                    Optional.of(new MemberEntity(1L, "a@a.com", "password"))
            );

            final MemberEntity member = memberService.findMember(memberAuthDto);

            assertThat(member.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("멤버가 존재하지 않으면 예외를 던진다.")
        void findMemberWithNotExist() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto("a@a.com", "password");
            given(memberDao.findByEmailAndPassword(any(), any())).willReturn(Optional.empty());

            assertThatThrownBy(() -> memberService.findMember(memberAuthDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("등록되지 않은 회원입니다.");
        }
    }
}

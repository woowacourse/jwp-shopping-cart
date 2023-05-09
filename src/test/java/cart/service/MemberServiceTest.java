package cart.service;

import cart.dto.member.MemberDto;
import cart.dto.member.MemberRequestDto;
import cart.entity.MemberEntity;
import cart.exception.DuplicateEmailException;
import cart.exception.InvalidMemberException;
import cart.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원을 추가한다.")
    void joinTest_success() {
        String email = "c@c.com";
        String password = "password3";
        MemberRequestDto request = new MemberRequestDto(email, password);
        MemberEntity entity = new MemberEntity(1L, email, password);
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(entity);

        MemberDto expectDto = MemberDto.fromEntity(entity);
        assertThat(memberService.join(request)).isEqualTo(expectDto);
    }

    @Test
    @DisplayName("회원을 추가할 때 동일한 email이 이미 존재하면 예외가 발생한다.")
    void joinTest_fail() {
        MemberRequestDto request = new MemberRequestDto("c@c.com", "password");
        when(memberRepository.findByEmail(any(String.class)))
                .thenThrow(new DuplicateEmailException("동일한 이메일이 2개 이상 존재합니다."));

        assertThatThrownBy(() -> memberService.join(request))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("동일한 이메일이 2개 이상 존재합니다.");
    }

    @Test
    @DisplayName("ID에 해당하는 회원을 조회한다.")
    void findByIdTest_success() {
        Long id = 1L;
        MemberEntity entity = new MemberEntity(id, "email@email.com", "password");
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(entity));

        MemberDto expectDto = MemberDto.fromEntity(entity);
        assertThat(memberService.findById(id)).isEqualTo(expectDto);
    }

    @Test
    @DisplayName("ID에 해당하는 회원이 없으면 예외가 발생한다.")
    void findByIdTest_fail() {
        Long id = 1L;
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> memberService.findById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id와 일치하는 회원이 없습니다.");
    }

    @Test
    @DisplayName("email에 해당하는 회원을 조회한다.")
    void findByEmailTest_success() {
        String email = "email@email.com";
        MemberEntity entity = new MemberEntity(1L, email, "password");
        when(memberRepository.findByEmail(any(String.class))).thenReturn(Optional.of(entity));

        MemberDto expectDto = MemberDto.fromEntity(entity);
        assertThat(memberService.findByEmail(email)).isEqualTo(expectDto);
    }

    @Test
    @DisplayName("email에 해당하는 회원이 없으면 예외가 발생한다.")
    void findByEmailTest_fail() {
        String email = "no@no.com";
        when(memberRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> memberService.findByEmail(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("email과 일치하는 회원이 없습니다.");
    }

    @Test
    @DisplayName("모든 회원을 조회한다.")
    void findAllTest() {
        MemberEntity entity1 = new MemberEntity(1L, "a@a.com", "password1");
        MemberEntity entity2 = new MemberEntity(2L, "b@b.com", "password2");
        when(memberRepository.findAll()).thenReturn(List.of(entity1, entity2));

        MemberDto expectDto1 = MemberDto.fromEntity(entity1);
        MemberDto expectDto2 = MemberDto.fromEntity(entity2);
        assertThat(memberService.findAll()).isEqualTo(List.of(expectDto1, expectDto2));
    }

    @Test
    @DisplayName("ID에 해당하는 회원 정보를 수정한다.")
    void updateByIdTest() {
        Long id = 1L;
        String newEmail = "new@new.com";
        String newPassword = "newPassword";
        MemberRequestDto request = new MemberRequestDto(newEmail, newPassword);
        doNothing().when(memberRepository).update(any(MemberEntity.class));

        MemberDto expectDto = MemberDto.fromEntity(new MemberEntity(id, newEmail, newPassword));
        assertThat(memberService.updateById(request, id)).isEqualTo(expectDto);
    }

    @Test
    @DisplayName("ID에 해당하는 회원 정보를 삭제한다.")
    void deleteByIdTest() {
        Long id = 1L;
        doNothing().when(memberRepository).deleteById(any(Long.class));

        assertThatNoException().isThrownBy(() -> memberService.deleteById(id));
    }
}
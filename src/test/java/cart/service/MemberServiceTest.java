package cart.service;

import cart.controller.dto.MemberDto;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberService.class)
class MemberServiceTest {

    @MockBean
    private MemberDao memberDao;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("사용자 정보를 저장한다.")
    void save() {
        // given
        final MemberDto memberDto = new MemberDto(1L, "journey@gmail.com", "password", "져니", "010-1234-5678");
        final MemberEntity memberEntity = new MemberEntity("journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(memberDao.insert(any())).thenReturn(1L);
        when(memberDao.findById(1L)).thenReturn(Optional.of(memberEntity));

        // when
        final long savedUserId = memberService.save(memberDto);

        // then
        final MemberDto result = memberService.getById(savedUserId);
        assertThat(result)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효한 사용자 아이디가 주어지면, 사용자 정보를 반환한다.")
    void getById_success() {
        // given
        final MemberEntity memberEntity = new MemberEntity("journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(memberDao.findById(any())).thenReturn(Optional.of(memberEntity));

        // when
        final MemberDto memberDto = memberService.getById(1L);

        // then
        assertThat(memberDto)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효하지 않은 사용자 아이디가 주어지면, 예외가 발생한다.")
    void getById_fail() {
        assertThatThrownBy(() -> memberService.getById(1L))
                .isInstanceOf(GlobalException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    @DisplayName("전체 사용자를 조회한다.")
    void getUsers() {
        // given
        final List<MemberEntity> userEntities = List.of(
                new MemberEntity(1L, "journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678"),
                new MemberEntity(2L, "koda@gmail.com", "a29kYWlzc29jdXRl", "코다", "010-4321-8765"),
                new MemberEntity(3L, "baron@gmail.com", "YmFyb25iYWJv", "바론", "010-1111-2222")
        );
        when(memberDao.findAll()).thenReturn(userEntities);

        // when
        final List<MemberDto> users = memberService.getMembers();

        // then
        assertThat(users).hasSize(3);
        assertThat(users)
                .extracting("id", "email", "password", "nickname", "telephone")
                .containsExactly(tuple(1L, "journey@gmail.com", "password", "져니", "010-1234-5678"),
                        tuple(2L, "koda@gmail.com", "kodaissocute", "코다", "010-4321-8765"),
                        tuple(3L, "baron@gmail.com", "baronbabo", "바론", "010-1111-2222"));
    }
}

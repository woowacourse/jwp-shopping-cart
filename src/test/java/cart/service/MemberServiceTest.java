package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.MemberEntity;
import cart.service.dto.MemberRequest;
import cart.service.dto.MemberResponse;
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
    @DisplayName("사용자 정보를 저장한다.")
    void save() {
        // given
        final MemberRequest memberRequest = new MemberRequest(1L, "USER", "journey@gmail.com",
            "password", "져니", "010-1234-5678");
        final MemberEntity memberEntity = new MemberEntity("journey@gmail.com", "USER",
            "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(memberDao.findByEmail(any())).thenReturn(Optional.empty());
        when(memberDao.insert(any())).thenReturn(1L);
        when(memberDao.findById(1L)).thenReturn(Optional.of(memberEntity));

        // when
        final long savedUserId = memberService.save(memberRequest);

        // then
        final MemberResponse result = memberService.getById(savedUserId);
        assertThat(result)
            .extracting("email", "role", "password", "nickname", "telephone")
            .containsExactly("journey@gmail.com", "USER", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("이미 존재하는 사용자 이메일이 주어지면, 예외가 발생한다.")
    void save_duplicate_fail() {
        // given
        final MemberRequest memberRequest = new MemberRequest(1L, "USER", "journey@gmail.com",
            "password", "져니", "010-1234-5678");
        final MemberEntity memberEntity = new MemberEntity("journey@gmail.com", "USER",
            "cGFzc3dvcmQ=", "져니", "010-1234-5678");

        when(memberDao.findByEmail(any())).thenReturn(Optional.of(memberEntity));

        // when, then
        assertThatThrownBy(() -> memberService.save(memberRequest))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.MEMBER_DUPLICATE_EMAIL);
    }


    @Test
    @DisplayName("유효한 사용자 아이디가 주어지면, 사용자 정보를 반환한다.")
    void getById_success() {
        // given
        final MemberEntity memberEntity = new MemberEntity("journey@gmail.com", "USER",
            "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(memberDao.findById(any())).thenReturn(Optional.of(memberEntity));

        // when
        final MemberResponse memberResponse = memberService.getById(1L);

        // then
        assertThat(memberResponse)
            .extracting("email", "role", "password", "nickname", "telephone")
            .containsExactly("journey@gmail.com", "USER", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
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
    @DisplayName("유효한 사용자 이메일과 비밀번호가 주어지면, 사용자 정보를 반환한다.")
    void getByEmailAndPassword_success() {
        // given
        final MemberEntity memberEntity = new MemberEntity("journey@gmail.com", "USER",
            "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(memberDao.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(memberEntity));

        // when
        final MemberResponse memberResponse = memberService.getByEmailAndPassword("journey@gmail.com", "cGFzc3dvcmQ=");

        // then
        assertThat(memberResponse)
            .extracting("email", "role", "password", "nickname", "telephone")
            .containsExactly("journey@gmail.com", "USER", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효하지 않은 사용자 이메일과 비밀번호가 주어지면, 예외가 발생한다.")
    void getByEmailAndPassword_invalid_fail() {
        assertThatThrownBy(() -> memberService.getByEmailAndPassword("journey@gmail.com", "cGFzc3dvcmQ="))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    @DisplayName("전체 사용자를 조회한다.")
    void getUsers() {
        // given
        final List<MemberEntity> userEntities = List.of(
            new MemberEntity(1L, "USER", "journey@gmail.com", "cGFzc3dvcmQ=", "져니",
                "010-1234-5678"),
            new MemberEntity(2L, "USER", "koda@gmail.com", "a29kYWlzc29jdXRl", "코다",
                "010-4321-8765"),
            new MemberEntity(3L, "USER", "baron@gmail.com", "YmFyb25iYWJv", "바론", "010-1111-2222")
        );
        when(memberDao.findAll()).thenReturn(userEntities);

        // when
        final List<MemberResponse> members = memberService.getMembers();

        // then
        assertThat(members).hasSize(3);
        assertThat(members)
            .extracting("id", "role", "email", "password", "nickname", "telephone")
            .containsExactly(
                tuple(1L, "USER", "journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678"),
                tuple(2L, "USER", "koda@gmail.com", "a29kYWlzc29jdXRl", "코다", "010-4321-8765"),
                tuple(3L, "USER", "baron@gmail.com", "YmFyb25iYWJv", "바론", "010-1111-2222"));
    }
}

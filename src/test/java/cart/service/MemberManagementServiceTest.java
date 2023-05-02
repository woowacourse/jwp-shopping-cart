package cart.service;

import cart.dao.JdbcMemberDao;
import cart.dto.MemberDto;
import cart.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberManagementServiceTest {

    @InjectMocks
    MemberManagementService managementService;

    @Mock
    JdbcMemberDao memberDao;

    @Nested
    @DisplayName("사용자 목록을 조회하는 findAll 메서드 테스트")
    class FindAllTest {

        @DisplayName("모든 사용자를 가져와서 반환하는지 확인한다")
        @Test
        void successTest() {
            final List<MemberEntity> data = List.of(
                    MemberEntity.of(1L, "irene@email.com", "password1"),
                    MemberEntity.of(2L, "hihi@email.com", "password2")
            );
            when(memberDao.selectAll()).thenReturn(data);

            final List<MemberDto> memberDtos = managementService.findAll();

            assertAll(
                    () -> assertThat(memberDtos.size()).isEqualTo(data.size()),
                    () -> assertThat(memberDtos.get(0).getId()).isEqualTo(1L),
                    () -> assertThat(memberDtos.get(0).getEmail()).isEqualTo("irene@email.com"),
                    () -> assertThat(memberDtos.get(0).getPassword()).isEqualTo("password1"),
                    () -> assertThat(memberDtos.get(1).getId()).isEqualTo(2L),
                    () -> assertThat(memberDtos.get(1).getEmail()).isEqualTo("hihi@email.com"),
                    () -> assertThat(memberDtos.get(1).getPassword()).isEqualTo("password2")
                    );
        }
    }
}

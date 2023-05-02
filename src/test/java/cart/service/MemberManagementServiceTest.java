package cart.service;

import cart.dao.member.JdbcMemberDao;
import cart.domain.member.Member;
import cart.dto.MemberResponse;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("모든 상품의 도메인을 가져와서 반환하는지 확인한다")
    void findAllTest() {
        final List<Member> data = List.of(
                Member.of(1L, "test@test.com", "test"),
                Member.of(2L, "woowacourse@woowa.com", "pobi")
        );
        when(memberDao.selectAll()).thenReturn(data);

        final List<MemberResponse> memberResponses = managementService.findAll();

        assertAll(
                () -> assertThat(memberResponses.size()).isEqualTo(data.size()),
                () -> assertThat(memberResponses.get(0).getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(memberResponses.get(0).getPassword()).isEqualTo("test"),
                () -> assertThat(memberResponses.get(1).getEmail()).isEqualTo("woowacourse@woowa.com"),
                () -> assertThat(memberResponses.get(1).getPassword()).isEqualTo("pobi")
        );
    }
}

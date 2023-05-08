package cart.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dao.MemberDao;
import cart.dto.MemberResponse;
import cart.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberDao memberDao;

    @Test
    void 모든_회원_조회() {
        Mockito.when(memberDao.findAll())
                .thenReturn(List.of(
                        createMember(1L),
                        createMember(2L),
                        createMember(3L)
                ));

        final List<MemberResponse> result = memberService.findAll();

        assertThat(result.size()).isEqualTo(3);
    }

    private MemberEntity createMember(long id) {
        return new MemberEntity(id, "email", "password", "name", "address", 10);
    }
}

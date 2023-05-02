package cart.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dao.MemberDao;
import cart.dto.MemberResponse;
import cart.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;

@SpringBootTest
@MockitoSettings
class MemberServiceTest {

    @MockBean
    private MemberDao memberDao;

    @Autowired
    private MemberService memberService;

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

package cart.service;

import cart.dao.member.MemberDao;
import cart.entity.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("모든 멤버 조회 테스트")
    void findAll() {
        given(memberDao.findAll()).willReturn(List.of(
                new MemberEntity("test@naver.com", "test", "01012345678", "qwer1234"),
                new MemberEntity("test@gmail.com", "test", "01098765432", "qwer1234")
        ));

        Assertions.assertThat(memberService.findAll()).hasSize(2);
    }
}

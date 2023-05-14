package cart.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.dao.MemberDao;
import cart.dao.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @Mock
    MemberDao memberDao;

    @InjectMocks
    MemberService memberService;

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void findAll() {

        MemberEntity 우가 = new MemberEntity(1, "우가@naver.com", "1234");
        MemberEntity 로이 = new MemberEntity(2, "로이@naver.com", "1234");
        MemberEntity 제이미 = new MemberEntity(3, "제이미@naver.com", "1234");
        when(memberDao.findAll())
                .thenReturn(List.of(우가, 로이, 제이미));

        memberService.findAll();

        verify(memberDao, atLeastOnce()).findAll();
    }
}
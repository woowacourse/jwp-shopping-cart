package cart.service;

import static org.mockito.Mockito.verify;

import cart.dao.Dao;
import cart.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private Dao<Member> memberDao;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberDao);
    }

    @DisplayName("사용자 전체 조회 시 DB에서 정보를 조회한다")
    @Test
    void findAll() {
        // given
        // when
        memberService.findAll();

        // then
        verify(memberDao).findAll();
    }
}

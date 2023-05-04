package cart.service;

import cart.dto.MemberDto;
import cart.entity.Member;
import cart.exception.customExceptions.DataNotFoundException;
import cart.exception.customExceptions.NotUniqueValueException;
import cart.exception.customExceptions.PasswordNotMatchException;
import cart.repository.dao.memberDao.MemberDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberServiceTest {

    private MemberDao memberDao;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberDao = mock(MemberDao.class);
        memberService = new MemberService(memberDao);
    }

    @Test
    void 중복되는_이메일로_회원가입시_예외를_던진다() {
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "1234";
        MemberDto member = new MemberDto(email, name, password);

        when(memberDao.save(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.registerMember(member))
                .isInstanceOf(NotUniqueValueException.class)
                .hasMessage("중복되는 email입니다. 다른 이메일을 입력해주세요.");
    }

    @Test
    void 없는_이메일로_로그인시_예외를_던진다() {
        when(memberDao.findByEmail(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.loginMember("first@email.com", "randomPassword"))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 사용자가 존재하지 않습니다.");
    }

    @Test
    void 로그인시_비밀번호가_틀렸을때_예외를_던진다() {
        String wrongPassword = "2345";
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "1234";
        Member member = new Member(email, name, password);

        when(memberDao.findByEmail(any()))
                .thenReturn(Optional.of(member));

        assertThatThrownBy(() -> memberService.loginMember(email, wrongPassword))
                .isInstanceOf(PasswordNotMatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}

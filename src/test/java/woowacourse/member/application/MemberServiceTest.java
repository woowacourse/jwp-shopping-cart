package woowacourse.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.MemberFixture.createMemberRegisterRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.exception.DuplicateMemberEmailException;


@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDao memberDao;

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        MemberRegisterRequest memberRegisterRequest = createMemberRegisterRequest(EMAIL, PASSWORD, NAME);
        Long id = memberService.save(memberRegisterRequest);

        assertThat(id).isNotNull();
    }

    @DisplayName("회원을 저장할 때 동일한 이메일이 있으면 예외를 발생한다.")
    @Test
    void saveException() {
        memberDao.save(createMember(EMAIL, PASSWORD, NAME));

        MemberRegisterRequest memberRegisterRequest = createMemberRegisterRequest(EMAIL, PASSWORD, NAME);
        assertThatThrownBy(() -> memberService.save(memberRegisterRequest))
                .isInstanceOf(DuplicateMemberEmailException.class);
    }

    @DisplayName("로그인 성공 여부를 확인한다_성공")
    @Test
    void isLoginSuccess() {
        memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);

        assertThat(memberService.isLogin(tokenRequest)).isTrue();
    }

    @DisplayName("로그인 성공 여부를 확인한다_실패")
    @Test
    void isLoginFail() {
        memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        TokenRequest tokenRequest = new TokenRequest(EMAIL, "Fail1234!");

        assertThat(memberService.isLogin(tokenRequest)).isFalse();
    }
}

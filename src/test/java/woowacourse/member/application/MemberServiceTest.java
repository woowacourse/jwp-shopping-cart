package woowacourse.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.MemberFixture.createMemberRegisterRequest;
import static woowacourse.helper.fixture.MemberFixture.passwordEncoder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.dto.MemberNameUpdateRequest;
import woowacourse.member.dto.MemberPasswordUpdateRequest;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.dto.MemberResponse;
import woowacourse.member.exception.DuplicateMemberEmailException;
import woowacourse.member.exception.NoMemberException;
import woowacourse.member.exception.WrongPasswordException;


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
    void loginSuccess() {
        memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);

        assertThat(memberService.login(tokenRequest).getEmail()).isEqualTo(EMAIL);
    }

    @DisplayName("이메일이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void loginNoMember() {
        assertThatThrownBy(() -> memberService.login(new TokenRequest(EMAIL, PASSWORD)))
                .isInstanceOf(NoMemberException.class);
    }

    @DisplayName("비밀번호가 틀릴경우 예외가 발생한다.")
    @Test
    void loginWrongPassword() {
        memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        assertThatThrownBy(() -> memberService.login(new TokenRequest(EMAIL, "Fail1234!")))
                .isInstanceOf(WrongPasswordException.class);
    }

    @DisplayName("id로 회원정보를 조회한다.")
    @Test
    void getMemberInformation() {
        Long id = memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        MemberResponse memberResponse = memberService.getMemberInformation(id);

        assertAll(
                () -> assertThat(memberResponse.getId()).isNotNull(),
                () -> assertThat(memberResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(memberResponse.getName()).isEqualTo(NAME)
        );
    }

    @DisplayName("회원이 없을 경우 예외를 발생한다.")
    @Test
    void getMemberInformationNoMember() {
        assertThatThrownBy(() -> memberService.getMemberInformation(0L))
                .isInstanceOf(NoMemberException.class);
    }

    @DisplayName("회원 이름을 수정한다.")
    @Test
    void updateName() {
        Long id = memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        memberService.updateName(id, new MemberNameUpdateRequest("MARU"));

        assertThat(memberService.getMemberInformation(id).getName()).isEqualTo("MARU");
    }

    @DisplayName("회원 비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        Long id = memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        memberService.updatePassword(id, new MemberPasswordUpdateRequest(PASSWORD,"Maru1234!"));

        Member member = memberDao.findById(id).get();

        assertThatNoException()
                .isThrownBy(() -> member.validateWrongPassword("Maru1234!", passwordEncoder()));
    }

    @DisplayName("멤버를 삭제한다.")
    @Test
    void deleteById() {
        Long id = memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        memberService.deleteById(id);

        assertThat(memberDao.findById(id).isEmpty()).isTrue();
    }

    @DisplayName("이메일 중복이 있으면 에러를 발생한다.")
    @Test
    void validateDuplicateEmailExist() {
        memberService.save(createMemberRegisterRequest(EMAIL, PASSWORD, NAME));
        assertThatThrownBy(() -> memberService.validateDuplicateEmail(EMAIL))
                .isInstanceOf(DuplicateMemberEmailException.class);
    }
}

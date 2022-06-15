package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.MemberService;
import woowacourse.shoppingcart.dao.MemberDao;
import woowacourse.shoppingcart.dto.request.MemberCreateRequest;
import woowacourse.shoppingcart.dto.request.MemberUpdateRequest;
import woowacourse.shoppingcart.dto.request.PasswordUpdateRequest;
import woowacourse.shoppingcart.dto.response.MemberResponse;
import woowacourse.auth.exception.AuthorizationException;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDao memberDao;

    @DisplayName("회원 객체를 생성하고 DB에 저장한다.")
    @Test
    void saveMember() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        memberService.save(memberCreateRequest);
    }

    @DisplayName("이미 존재하는 이메일로 회원을 생성하려고 하면 예외를 반환한다.")
    @Test
    void saveMember_DuplicatedEmail() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        memberService.save(memberCreateRequest);

        assertThatThrownBy(() -> memberService.save(memberCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일 주소입니다.");
    }

    @DisplayName("이메일과 수정할 회원 정보를 받아 회원 정보를 수정한다.")
    @Test
    void updateMember() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        Long memberId = memberService.save(memberCreateRequest);

        memberService.updateMember(memberId, new MemberUpdateRequest("바뀐닉네임"));
        String nickname = memberService.find(memberId)
                .getNickname();

        assertThat(nickname).isEqualTo("바뀐닉네임");
    }

    @DisplayName("존재하지 않는 회원의 정보를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updateMember_NotFoundMember() {
        assertThatThrownBy(() -> memberService.updateMember(Long.MAX_VALUE, new MemberUpdateRequest("바뀐닉네임")))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원 정보를 찾지 못했습니다.");
    }

    @DisplayName("올바르지 않은 형식의 닉네임으로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidNicknameFormat() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        Long memberId = memberService.save(memberCreateRequest);

        assertThatThrownBy(() -> memberService.updateMember(memberId, new MemberUpdateRequest("1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 올바르지 않습니다.");
    }

    @DisplayName("비밀번호를 받아 수정한다.")
    @Test
    void updatePassword() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        Long memberId = memberService.save(memberCreateRequest);

        memberService.updatePassword(memberId, new PasswordUpdateRequest("1q2w3e4r@"));
        String password = memberDao.findById(memberId)
                .orElseGet(() -> fail(""))
                .getPassword();

        assertThat(password).isEqualTo("1q2w3e4r@");
    }

    @DisplayName("회원의 정보를 반환한다.")
    @Test
    void findMember() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        Long memberId = memberService.save(memberCreateRequest);

        MemberResponse memberResponse = memberService.find(memberId);

        assertThat(memberResponse.getEmail()).isEqualTo("abc@woowahan.com");
        assertThat(memberResponse.getNickname()).isEqualTo("닉네임");
    }

    @DisplayName("존재하지 않는 회원의 정보를 요청하면 예외를 반환한다.")
    @Test
    void findMember_NotFoundMember() {
        assertThatThrownBy(() -> memberService.find(Long.MAX_VALUE))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원 정보를 찾지 못했습니다.");
    }

    @DisplayName("존재하지 않는 회원의 비밀번호를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_NotFoundMember() {
        assertThatThrownBy(() -> memberService.updatePassword(Long.MAX_VALUE, new PasswordUpdateRequest("1q2w3e4r@")))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원 정보를 찾지 못했습니다.");
    }

    @DisplayName("올바르지 않은 형식의 비밀번호로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidPasswordFormat() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        Long memberId = memberService.save(memberCreateRequest);

        assertThatThrownBy(() -> memberService.updatePassword(memberId, new PasswordUpdateRequest("1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("회원 정보를 삭제한다.")
    @Test
    void deleteMember() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        Long memberId = memberService.save(memberCreateRequest);

        memberService.delete(memberId);
        boolean actual = memberService.existsEmail("abc@woowahan.com");

        assertThat(actual).isFalse();
    }

    @DisplayName("존재하는 이메일인지 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, true", "abc@naver.com, false"})
    void existsEmail(String email, boolean expected) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        Long memberId = memberService.save(memberCreateRequest);

        boolean actual = memberService.existsEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("올바르지 않은 이메일 형식으로 이메일이 존재하는지 확인하려하면 예외를 반환한다.")
    @Test
    void existsEmail_InvalidFormat() {
        String invalid = "abc";
        assertThatThrownBy(() -> memberService.existsEmail(invalid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("존재하지 않는 회원을 삭제하려 하면 예외를 반환한다.")
    @Test
    void deleteMember_NotFoundMember() {
        assertThatThrownBy(() -> memberService.delete(Long.MAX_VALUE))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원 정보를 찾지 못했습니다.");
    }
}

package woowacourse.auth.application;

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
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Member;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordUpdateRequest;
import woowacourse.auth.dto.response.MemberResponse;
import woowacourse.auth.exception.AuthorizationException;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDao memberDao;

    @DisplayName("이메일과 수정할 회원 정보를 받아 회원 정보를 수정한다.")
    @Test
    void updateMember() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberService.updateMember("abc@woowahan.com", new MemberUpdateRequest("바뀐닉네임"));
        String nickname = memberService.find("abc@woowahan.com")
                .getNickname();

        assertThat(nickname).isEqualTo("바뀐닉네임");
    }

    @DisplayName("존재하지 않는 회원의 정보를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updateMember_NotFoundMember() {
        assertThatThrownBy(() -> memberService.updateMember("abc@woowahan.com", new MemberUpdateRequest("바뀐닉네임")))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원 정보를 찾지 못했습니다.");
    }

    @DisplayName("올바르지 않은 형식의 닉네임으로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidNicknameFormat() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        assertThatThrownBy(() -> memberService.updateMember("abc@woowahan.com", new MemberUpdateRequest("1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일과 비밀번호를 받아 비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberService.updatePassword("abc@woowahan.com", new PasswordUpdateRequest("1q2w3e4r@"));
        String password = memberDao.findByEmail("abc@woowahan.com")
                .orElseGet(() -> fail(""))
                .getPassword();

        assertThat(password).isEqualTo("1q2w3e4r@");
    }

    @DisplayName("회원의 정보를 반환한다.")
    @Test
    void findMember() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        MemberResponse memberResponse = memberService.find("abc@woowahan.com");

        assertThat(memberResponse.getEmail()).isEqualTo("abc@woowahan.com");
        assertThat(memberResponse.getNickname()).isEqualTo("닉네임");
    }

    @DisplayName("존재하지 않는 회원의 정보를 요청하면 예외를 반환한다.")
    @Test
    void findMember_NotFoundMember() {
        assertThatThrownBy(() -> memberService.find("abc@woowahan.com"))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원 정보를 찾지 못했습니다.");
    }

    @DisplayName("존재하지 않는 회원의 비밀번호를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_NotFoundMember() {
        assertThatThrownBy(() -> memberService.updatePassword("abc@woowahan.com", new PasswordUpdateRequest("1q2w3e4r@")))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원 정보를 찾지 못했습니다.");
    }

    @DisplayName("올바르지 않은 형식의 비밀번호로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidPasswordFormat() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        assertThatThrownBy(() -> memberService.updatePassword("abc@woowahan.com", new PasswordUpdateRequest("1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일이 일치하는 회원 정보를 삭제한다.")
    @Test
    void deleteMember() {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

        memberService.delete("abc@woowahan.com");
        boolean actual = memberService.existsEmail("abc@woowahan.com");

        assertThat(actual).isFalse();
    }

    @DisplayName("존재하는 이메일인지 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, true", "abc@naver.com, false"})
    void existsEmail(String email, boolean expected) {
        Member member = new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);

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
        assertThatThrownBy(() -> memberService.delete("abc@woowahan.com"))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원 정보를 찾지 못했습니다.");
    }
}

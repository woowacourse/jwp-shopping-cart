package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.MemberRequest;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @DisplayName("회원 객체를 생성하고 DB에 저장한다.")
    @Test
    void saveMember() {
        MemberRequest memberRequest = new MemberRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        authService.save(memberRequest);
    }

    @DisplayName("이미 존재하는 이메일로 회원을 생성하려고 하면 예외를 반환한다.")
    @Test
    void saveMember_DuplicatedEmail() {
        MemberRequest memberRequest = new MemberRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        authService.save(memberRequest);

        assertThatThrownBy(() -> authService.save(memberRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일 주소입니다.");
    }
}

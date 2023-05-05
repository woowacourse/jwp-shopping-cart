package cart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("findMemberId 성공 테스트")
    @Test
    void findMemberId() {
        assertDoesNotThrow(() -> memberService.findMemberId("dino96@naver.com", "jjongwa96"));
    }

    @DisplayName("findMemberId 실패 테스트")
    @Test
    void failFindMemberId() {
        assertAll(
                () -> assertThatThrownBy(() -> memberService.findMemberId("dino99@naver.com", "jjongwa96"))
                        .hasMessage("해당하는 유저가 존재하지 않습니다."),
                () -> assertThatThrownBy(() -> memberService.findMemberId("dino96@naver.com", "jjongwa99"))
                        .hasMessage("해당하는 유저가 존재하지 않습니다.")
        );
    }

}
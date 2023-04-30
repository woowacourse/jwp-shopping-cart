package cart.service;

import cart.dto.MemberRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    private final MemberRegisterRequest memberRegisterRequest =
            new MemberRegisterRequest("SeongHa", "seongha@gmail.com", "1234");


    @Test
    @DisplayName("사용자를 등록한다.")
    void register() {
        // when, then
        Assertions.assertDoesNotThrow(() -> memberService.register(memberRegisterRequest));
    }
}

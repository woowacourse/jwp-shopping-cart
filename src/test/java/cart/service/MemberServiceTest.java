package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dto.application.MemberDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberServiceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 사용자명과_비밀번호가_일치하는_계정이_있을_경우_true를_반환한다() {
        final MemberDto memberDto = new MemberDto("user1@email.com", "password1");
        final boolean isMember = memberService.isMember(memberDto);

        assertThat(isMember).isTrue();
    }

    @Test
    void 사용자명과_비밀번호가_일치하는_계정이_없을_경우_false를_반환한다() {
        final MemberDto memberDto = new MemberDto("user3@email.com", "password1");
        final boolean isMember = memberService.isMember(memberDto);

        assertThat(isMember).isFalse();
    }
}

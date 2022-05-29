package woowacourse.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dto.MemberRegisterRequest;


@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        MemberRegisterRequest memberRegisterRequest = createMemberRegisterRequest(EMAIL, PASSWORD, NAME);
        Long id = memberService.save(memberRegisterRequest);

        assertThat(id).isNotNull();
    }
}

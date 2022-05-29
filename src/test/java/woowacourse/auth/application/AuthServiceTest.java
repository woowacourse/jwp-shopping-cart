package woowacourse.auth.application;


import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.member.dao.MemberDao;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberDao memberDao;

    @DisplayName("알맞은 로그인 정보를 입력해서 토큰을 반환한다.")
    @Test
    void generateToken() {
        memberDao.save(createMember(EMAIL, PASSWORD, NAME));

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        TokenResponse tokenResponse = authService.generateToken(tokenRequest);

        assertThat(tokenResponse.getAccessToken()).isNotNull();
    }
}
